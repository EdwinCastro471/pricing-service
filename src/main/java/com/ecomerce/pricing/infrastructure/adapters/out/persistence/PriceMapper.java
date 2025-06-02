package com.ecomerce.pricing.infrastructure.adapters.out.persistence;

import com.ecomerce.pricing.domain.models.Price;
import com.ecomerce.pricing.infrastructure.adapters.out.persistence.entities.PriceEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {
    Price toDomain(PriceEntity entity);
}
