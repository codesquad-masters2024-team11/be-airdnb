package com.yourbnb.reservation.model.dto;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import com.yourbnb.reservation.model.Reservation;

import java.time.LocalDate;

public record ReservationCreationRequest(LocalDate checkInDate,
                                         LocalDate checkOutDate,
                                         Integer visitorNumber,
                                         String memberId,
                                         Long accommodationId) {

    public Reservation toEntity(Member member, Accommodation accommodation, int totalPrice) {
        return Reservation.of(checkInDate, checkOutDate, visitorNumber, totalPrice, member, accommodation);
    }
}
