package com.ecomerce.pricing;

import com.ecomerce.pricing.domain.models.Price;
import com.ecomerce.pricing.infrastructure.adapters.out.persistence.JpaPriceRepositoryAdapter;
import com.ecomerce.pricing.infrastructure.adapters.out.persistence.PriceMapper;
import com.ecomerce.pricing.infrastructure.adapters.out.persistence.PriceRepository;
import com.ecomerce.pricing.infrastructure.adapters.out.persistence.entities.PriceEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaPriceRepositoryAdapterTest {
    @Mock
    private PriceRepository priceRepository;

    @Mock
    private PriceMapper mapper;

    @InjectMocks
    private JpaPriceRepositoryAdapter adapter;

    @Test
    void whenEntityFound_thenReturnsDomainObject() {
        LocalDateTime now = LocalDateTime.now();

        PriceEntity entity = new PriceEntity();
        Price domain = Price.builder().productId(1L).build();

        when(priceRepository.findByBrandIdAndProductIdAndDate(1L, 1L, now))
                .thenReturn(Optional.of(entity));

        when(mapper.toDomain(entity)).thenReturn(domain);

        Optional<Price> result = adapter.findByBrandIdAndProductIdAndDate(1L, 1L, now);

        assertThat(result).isPresent();
        assertThat(result.map(Price::getProductId)).hasValue(1L);
    }

    @Test
    void whenEntityNotFound_thenReturnsEmpty() {
        LocalDateTime now = LocalDateTime.now();

        when(priceRepository.findByBrandIdAndProductIdAndDate(1L, 1L, now))
                .thenReturn(Optional.empty());

        Optional<Price> result = adapter.findByBrandIdAndProductIdAndDate(1L, 1L, now);

        assertThat(result).isEmpty();
    }
}
