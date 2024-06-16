package com.yourbnb.reservation.service;

import com.yourbnb.accommodation.exception.AccommodationNotFoundException;
import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.member.exception.MemberNotFoundException;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.repository.MemberRepository;
import com.yourbnb.reservation.exception.ReservationNotFoundException;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.reservation.model.dto.ReservationCreationRequest;
import com.yourbnb.reservation.model.dto.ReservationResponse;
import com.yourbnb.reservation.model.dto.ReservationUpdateRequest;
import com.yourbnb.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private static final double TAX = 1.1;

    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    @Transactional
    public Reservation createReservation(ReservationCreationRequest reservationCreationRequest) {
        Member member = memberRepository.findById(reservationCreationRequest.memberId())
                .orElseThrow(() -> new MemberNotFoundException(reservationCreationRequest.memberId()));

        Accommodation accommodation = accommodationRepository.findById(reservationCreationRequest.accommodationId())
                .orElseThrow(() -> new AccommodationNotFoundException(reservationCreationRequest.accommodationId()));

        int totalPrice = calculateTotalPrice(reservationCreationRequest.checkInDate(), reservationCreationRequest.checkOutDate(), accommodation);

        Reservation reservation = reservationCreationRequest.toEntity(member, accommodation, totalPrice);

        return reservationRepository.save(reservation);
    }


    public Optional<ReservationResponse> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationResponse::from);
    }


    public List<ReservationResponse> getReservationsByMemberId(String memberId) {
        return reservationRepository.findByMember_MemberId(memberId).stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());

    }


    public List<ReservationResponse> getReservationsByAccommodationId(Long accommodationId) {
        return reservationRepository.findByAccommodationId(accommodationId).stream()
                .map(ReservationResponse::from)
                .collect(Collectors.toList());
    }


    @Transactional
    public Reservation updateReservation(Long reservationId, ReservationUpdateRequest reservationUpdateRequest) {
        // 1) 업데이트할 예약건 찾기
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(()-> new ReservationNotFoundException(reservationId));

        // 2) 관련 숙소 정보 조회하기
        Accommodation accommodation = accommodationRepository.findById(reservation.getAccommodation().getId())
                .orElseThrow(()-> new AccommodationNotFoundException(reservation.getAccommodation().getId()));

        int totalPrice = calculateTotalPrice(reservationUpdateRequest.checkInDate(), reservationUpdateRequest.checkOutDate(), accommodation);

        // 2) 업데이트
        reservation.update(reservationUpdateRequest, totalPrice);

        // 3) 반환
        return reservationRepository.save(reservation);
    }


    private int calculateTotalPrice(LocalDate checkInDate, LocalDate checkOutDate, Accommodation accommodation) {
        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return (int) (daysBetween * accommodation.getPrice() * TAX + accommodation.getCleaningFee());
    }
}
