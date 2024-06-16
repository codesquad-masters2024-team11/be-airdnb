package com.yourbnb.reservation.model.dto;

import com.yourbnb.reservation.model.Reservation;
import lombok.Getter;

@Getter
public class ReservationDeleteResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "예약 삭제 성공! 예약 #%d";

    private final Long id;
    private final String message;

    private ReservationDeleteResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }

    public static ReservationDeleteResponse from(Long id) {
        String message = generateSuccessMessage(id);
        return new ReservationDeleteResponse(id, message);
    }

    private static String generateSuccessMessage(Long id) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, id);
    }
}
