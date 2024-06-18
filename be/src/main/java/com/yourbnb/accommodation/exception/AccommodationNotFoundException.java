package com.yourbnb.accommodation.exception;

import com.yourbnb.global.exception.NotFoundException;

public class AccommodationNotFoundException extends NotFoundException {
    public AccommodationNotFoundException(Long resourceId) {
        super(String.format("아이디가 %s인 숙소", resourceId));
    }
}
