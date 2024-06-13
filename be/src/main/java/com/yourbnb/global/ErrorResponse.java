package com.yourbnb.global;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ErrorResponse {
    private final String errorCode;
    private final String errorMsg;
}
