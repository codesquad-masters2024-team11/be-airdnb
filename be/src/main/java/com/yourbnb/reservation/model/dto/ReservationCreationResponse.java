package com.yourbnb.reservation.model.dto;

import com.yourbnb.reservation.model.Reservation;
import lombok.Getter;

@Getter
public class ReservationCreationResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "예약 생성 성공! 예약 #%d 총 금액 : %d";

    private final Long id;
    private final Integer totalPrice;
    private final String message;

    public ReservationCreationResponse(Reservation reservation) {
        this.id = reservation.getId();
        this.totalPrice = reservation.getTotalPrice();
        this.message = generateSuccessMessage(reservation);
    }

    private String generateSuccessMessage(Reservation reservation) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, reservation.getId(), reservation.getTotalPrice());
    }

    public static ReservationCreationResponse from(Reservation reservation) {
        return new ReservationCreationResponse(reservation);
    }
}
