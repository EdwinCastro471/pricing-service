package com.ecomerce.pricing.infrastructure.adapters.out.persistence;


import com.ecomerce.pricing.application.ports.out.PriceRepositoryPort;
import com.ecomerce.pricing.domain.models.Price;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Repository
public class JpaPriceRepositoryAdapter implements PriceRepositoryPort {
    private final PriceRepository repository;
    private final PriceMapper mapper;

    @Override
    public Optional<Price> findByBrandIdAndProductIdAndDate(
            Long brandId,
            Long productId,
            LocalDateTime date
    ) {
        return repository.findByBrandIdAndProductIdAndDate(brandId, productId, date)
                .map(mapper::toDomain);
    }
}
