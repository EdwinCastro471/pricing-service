package com.ecomerce.pricing.domain.exceptions;

import java.time.LocalDateTime;

public class PriceNotFoundException extends RuntimeException {

    public PriceNotFoundException(String message) {
        super(message);
    }

    public PriceNotFoundException(Long productId, Long brandId, LocalDateTime date) {
        super(String.format(
                "Price not found for productId: %d, brandId: %d, and application date: %s",
                productId, brandId, date.toString()
        ));
    }
}