package com.yourbnb.global.exception;

import lombok.Getter;

public class ResourceAccessDeniedException extends RuntimeException {
    @Getter
    private final String errorCode;
    @Getter
    private final String errorMsg;

    public ResourceAccessDeniedException(String errorMsg) {
        super();
        this.errorCode = "RESOURCE_DENIED";
        this.errorMsg = errorMsg;
    }
}
