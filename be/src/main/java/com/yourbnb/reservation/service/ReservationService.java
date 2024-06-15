package com.yourbnb.reservation.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.repository.MemberRepository;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.reservation.model.dto.ReservationCreationRequest;
import com.yourbnb.reservation.model.dto.ReservationResponse;
import com.yourbnb.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Accommodation accommodation = accommodationRepository.findById(reservationCreationRequest.accommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        // TODO : 총 금액에 tax 랑 cleaning fee 더하기!? + tax 10% , cleaning fee
        long daysBetween = ChronoUnit.DAYS.between(reservationCreationRequest.checkInDate(), reservationCreationRequest.checkOutDate());
        int totalPrice = (int) (daysBetween * accommodation.getPrice() * TAX + accommodation.getCleaningFee());

        Reservation reservation = reservationCreationRequest.toEntity(member, accommodation, totalPrice);

        return reservationRepository.save(reservation);
    }

    public Optional<ReservationResponse> getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationResponse::from);
    }

//    public List<ReservationResponse> getReservationsByMemberId(String memberId) {
//        return reservationRepository.findByMemberId(memberId);
//    }
//
//    public List<ReservationResponse> getReservationsByAccommodationId(Long accommodationId) {
//        return reservationRepository.findByAccommodationId(accommodationId);
//    }
}
