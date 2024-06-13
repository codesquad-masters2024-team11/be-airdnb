package com.yourbnb.global.exception;

import lombok.Getter;

public class NotFoundException extends RuntimeException {
    private final String ERROR_CODE_FORMAT = "NOT_FOUND_%s";
    private final String ERROR_MSG_FORMAT = "%s은/는 존재하지 않습니다.";

    @Getter
    private final String errorCode;
    @Getter
    private final String errorMsg;

    public NotFoundException(String resourceName) {
        super();
        this.errorCode = String.format(ERROR_CODE_FORMAT, resourceName);
        this.errorMsg = String.format(ERROR_MSG_FORMAT, resourceName);
    }
}
