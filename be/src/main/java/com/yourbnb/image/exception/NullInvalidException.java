package com.yourbnb.image.exception;

import com.yourbnb.global.exception.InvalidException;

public class NullInvalidException extends InvalidException {
    public NullInvalidException() {
        super("NULL");
    }
}
