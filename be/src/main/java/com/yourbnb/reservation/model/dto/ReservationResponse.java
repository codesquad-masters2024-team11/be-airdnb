package com.yourbnb.reservation.model.dto;

import com.yourbnb.reservation.model.Reservation;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReservationResponse {

    private Long id;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private Integer visitorNumber;
    private Integer totalPrice;

    private ReservationResponse(Long id, LocalDate checkInDate, LocalDate checkOutDate, Integer visitorNumber, Integer totalPrice) {
        this.id = id;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.visitorNumber = visitorNumber;
        this.totalPrice = totalPrice;
    }

    public static ReservationResponse from (Reservation reservation) {
        return new ReservationResponse(
                reservation.getId(),
                reservation.getCheckInDate(),
                reservation.getCheckOutDate(),
                reservation.getVisitorNumber(),
                reservation.getTotalPrice()
        );
    }
}
