package com.yourbnb.accommodation.exception;

import com.yourbnb.global.exception.NotFoundException;

public class AccommodationTypeNotFoundException extends NotFoundException {
    public AccommodationTypeNotFoundException(long resourceId) {
        super(String.format("아이디가 %s인 숙소 타입", resourceId));
    }
}
