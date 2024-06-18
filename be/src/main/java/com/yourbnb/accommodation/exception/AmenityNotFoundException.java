package com.yourbnb.accommodation.exception;

import com.yourbnb.global.exception.NotFoundException;

public class AmenityNotFoundException extends NotFoundException {
    public AmenityNotFoundException(Long resourceId) {
        super(String.format("아이디가 %s인 어매니티", resourceId));
    }
}
