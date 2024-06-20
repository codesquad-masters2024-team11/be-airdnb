package com.yourbnb.search.exception;

import lombok.Getter;

public class InvalidCheckInCheckOutDateException extends RuntimeException {
    private final String ERROR_CODE = "INVALID_CHECK_IN_CHECK_OUT_DATE";
    private final String ERROR_MSG = "체크인 날짜는 체크아웃 날짜와 같거나 이후일 수 없습니다.";

    @Getter
    private final String errorCode;
    @Getter
    private final String errorMsg;

    public InvalidCheckInCheckOutDateException() {
        super();
        this.errorCode = ERROR_CODE;
        this.errorMsg = ERROR_MSG;
    }
}
