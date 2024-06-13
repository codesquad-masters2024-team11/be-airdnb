package com.yourbnb.reservation.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.repository.MemberRepository;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.reservation.model.dto.ReservationCreationRequest;
import com.yourbnb.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    public Reservation createReservation(ReservationCreationRequest reservationCreationRequest) {
        Member member = memberRepository.findById(reservationCreationRequest.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Accommodation accommodation = accommodationRepository.findById(reservationCreationRequest.accommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        // TODO : 총 금액에 tax 랑 cleaning fee 더하기!?
        long daysBetween = ChronoUnit.DAYS.between(reservationCreationRequest.checkInDate(), reservationCreationRequest.checkOutDate());
        int totalPrice = (int) (daysBetween * accommodation.getPrice());

        Reservation reservation = reservationCreationRequest.toEntity(member, accommodation, totalPrice);

        return reservationRepository.save(reservation);
    }
}
