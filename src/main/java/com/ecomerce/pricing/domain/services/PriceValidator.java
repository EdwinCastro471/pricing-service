package com.ecomerce.pricing.domain.services;

import com.ecomerce.pricing.domain.exceptions.InvalidPriceException;
import com.ecomerce.pricing.domain.models.Price;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class PriceValidator {

    public void validate(Price price, LocalDateTime applicationDate) {
        validatePrice(price);
        validateDateRange(price, applicationDate);
    }

    private void validatePrice(Price price) {
        if (price.getPrice() == null || price.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidPriceException("The price must be greater than zero.");
        }
    }

    private void validateDateRange(Price price, LocalDateTime date) {
        if (date.isBefore(price.getStartDate())) {
            throw new InvalidPriceException("The application date is before the valid range.");
        }
        if (date.isAfter(price.getEndDate())) {
            throw new InvalidPriceException("The application date is after the valid range.");
        }
    }
}