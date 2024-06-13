package com.yourbnb.global.exception;

import lombok.Getter;

public class InvalidException extends RuntimeException {
    private final String ERROR_CODE_FORMAT = "INVALID_%s";
    private final String ERROR_MSG_FORMAT = "%s은/는 유효하지 않은 값입니다.";

    @Getter
    private final String errorCode;
    @Getter
    private final String errorMsg;

    public InvalidException(String resourceName) {
        super();
        this.errorCode = String.format(ERROR_CODE_FORMAT, resourceName);
        this.errorMsg = String.format(ERROR_MSG_FORMAT, resourceName);
    }
}
