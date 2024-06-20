package com.yourbnb.global;

import com.yourbnb.global.exception.InvalidException;
import com.yourbnb.global.exception.NotFoundException;
import com.yourbnb.global.exception.ResourceAccessDeniedException;
import com.yourbnb.search.exception.InvalidCheckInCheckOutDateException;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        String errorCode = String.format("BINDING_ERROR_%s", fieldError.getField()).toUpperCase();
        String errorMsg = fieldError.getDefaultMessage();
        return new ErrorResponse(errorCode, errorMsg);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleIOException(NotFoundException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIOException(InvalidException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleIOException(IOException e) {
        String errorCode = "FILE_UPLOAD_ERROR";
        return new ErrorResponse(errorCode, e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse handleResourceAccessDeniedException(ResourceAccessDeniedException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMsg());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidCheckInCheckOutDateException(InvalidCheckInCheckOutDateException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMsg());
    }
}
