package com.patronage.calculator.exception;

public class ApiRequestsException extends RuntimeException{

    public ApiRequestsException(String message) {
        super(message);
    }

    public ApiRequestsException(String message, Throwable cause) {
        super(message, cause);
    }
}
