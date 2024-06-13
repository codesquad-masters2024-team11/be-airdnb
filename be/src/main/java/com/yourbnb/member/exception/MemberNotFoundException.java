package com.yourbnb.member.exception;

import com.yourbnb.global.exception.NotFoundException;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException(String resourceName) {
        super(resourceName);
    }
}
