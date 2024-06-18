package com.yourbnb.accommodation.exception;

import com.yourbnb.global.exception.NotFoundException;

public class AccommodationImageNotFoundException extends NotFoundException {
    public AccommodationImageNotFoundException(Long resourceId) {
        super(String.format("아이디가 %s인 숙소 이미지", resourceId));
    }
}
