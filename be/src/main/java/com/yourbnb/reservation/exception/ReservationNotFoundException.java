package com.yourbnb.reservation.exception;

import com.yourbnb.global.exception.NotFoundException;

public class ReservationNotFoundException extends NotFoundException {
    public ReservationNotFoundException(Long resourceId) {
        super(String.format("아이디가 %s인 예약", resourceId));
    }
}
