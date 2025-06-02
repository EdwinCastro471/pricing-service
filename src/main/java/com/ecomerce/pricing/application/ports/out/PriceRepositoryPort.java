package com.ecomerce.pricing.application.ports.out;

import com.ecomerce.pricing.domain.models.Price;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PriceRepositoryPort {
    Optional<Price> findByBrandIdAndProductIdAndDate(
            Long brandId,
            Long productId,
            LocalDateTime date
    );
}
