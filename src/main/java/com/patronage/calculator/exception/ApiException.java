package com.patronage.calculator.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException {

    private final String message;
    private final HttpStatus httpStatus;
    private final LocalDateTime timestamp;


    public ApiException(String message, HttpStatus httpStatus, LocalDateTime timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
