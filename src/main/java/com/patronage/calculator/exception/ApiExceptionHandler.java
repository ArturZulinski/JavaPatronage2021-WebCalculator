package com.patronage.calculator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {ApiRequestsException.class})
    public ResponseEntity<Object> handlerApiRequestException(ApiRequestsException e) {

        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ApiException apiException = new ApiException(
            e.getMessage(),
            badRequest,
            LocalDateTime.now()
        );

        return new ResponseEntity<>(apiException, badRequest);
    }
}
