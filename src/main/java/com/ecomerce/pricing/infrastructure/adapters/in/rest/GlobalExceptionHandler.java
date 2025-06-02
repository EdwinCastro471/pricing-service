package com.ecomerce.pricing.infrastructure.adapters.in.rest;

import com.ecomerce.pricing.domain.exceptions.DataInitializationException;
import com.ecomerce.pricing.domain.exceptions.InvalidDateRangeException;
import com.ecomerce.pricing.domain.exceptions.PriceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ResponseEntity<Object> handlePriceNotFound(
            PriceNotFoundException ex,
            WebRequest request
    ) {
        log.warn("Resource not found: {}", ex.getMessage());
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND,
                request
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
            Exception ex,
            WebRequest request
    ) {
        log.error("Unexpected error : {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Internal server error. Please contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler({
            IllegalArgumentException.class,
            InvalidDateRangeException.class,
            MethodArgumentNotValidException.class
    })
    public ResponseEntity<Object> handleBusinessExceptions(RuntimeException ex, WebRequest request) {
        log.warn("Business error: {}", ex.getMessage());
        return buildErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }

    @ExceptionHandler(DataInitializationException.class)
    public ResponseEntity<Object> handleDataInitializationException(
            DataInitializationException ex,
            WebRequest request
    ) {
        log.error("Initialization error: {}", ex.getMessage(), ex);
        return buildErrorResponse(
                "Internal server error. Please contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request
    ) {
        log.warn("Type mismatch: {}", ex.getMessage());
        return buildErrorResponse(
                "Invalid value for parameter: " + ex.getName(),
                HttpStatus.BAD_REQUEST,
                request
        );
    }


    private ResponseEntity<Object> buildErrorResponse(
            String message,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", request.getDescription(false).replace("uri=", ""));

        return new ResponseEntity<>(body, status);
    }
}