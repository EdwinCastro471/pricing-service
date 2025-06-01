package com.ecomerce.pricing.domain.exceptions;

public class DataInitializationException extends RuntimeException {
    public DataInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}