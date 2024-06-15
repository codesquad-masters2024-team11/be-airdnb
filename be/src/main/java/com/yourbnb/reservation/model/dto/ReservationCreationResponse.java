package com.yourbnb.reservation.model.dto;

import com.yourbnb.reservation.model.Reservation;
import lombok.Getter;

@Getter
public class ReservationCreationResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "예약 생성 성공! 예약 #%d 총 금액 : %d";

    private final Long id;
    private final Integer totalPrice;
    private final String message;

    private ReservationCreationResponse(Long id, Integer totalPrice, String message) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.message = message;
    }


    public static ReservationCreationResponse from(Reservation reservation) {
        Long id = reservation.getId();
        Integer totalPrice = reservation.getTotalPrice();
        String message = generateSuccessMessage(reservation);
        return new ReservationCreationResponse(id, totalPrice, message);
    }


    private static String generateSuccessMessage(Reservation reservation) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, reservation.getId(), reservation.getTotalPrice());
    }
}
