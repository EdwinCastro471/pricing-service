package com.ecomerce.pricing;

import com.ecomerce.pricing.application.dtos.PriceRequest;
import com.ecomerce.pricing.application.dtos.PriceResponse;
import com.ecomerce.pricing.application.ports.out.PriceRepositoryPort;
import com.ecomerce.pricing.application.usecases.GetPriceUseCaseImpl;
import com.ecomerce.pricing.domain.exceptions.PriceNotFoundException;
import com.ecomerce.pricing.domain.models.Price;
import com.ecomerce.pricing.domain.services.PriceValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetPriceUseCaseImplTest {
    @Mock
    private PriceRepositoryPort priceRepository;

    @Mock
    private PriceValidator priceValidator;

    @InjectMocks
    private GetPriceUseCaseImpl useCase;

    @Test
    void whenPriceExists_thenReturnsResponse() {
        LocalDateTime now = LocalDateTime.now();
        Price price = Price.builder()
                .productId(1L)
                .brandId(1L)
                .priceList(1)
                .startDate(now.minusDays(1))
                .endDate(now.plusDays(1))
                .price(BigDecimal.valueOf(50.00))
                .currency("EUR")
                .build();

        PriceRequest request = new PriceRequest(now, 1L, 1L);

        when(priceRepository.findByBrandIdAndProductIdAndDate(1L, 1L, now))
                .thenReturn(Optional.of(price));

        PriceResponse response = useCase.execute(request);

        assertThat(response).isNotNull();
        assertThat(response.getFinalPrice()).isEqualTo(BigDecimal.valueOf(50.00));
        verify(priceValidator).validate(price, now);
    }

    @Test
    void whenPriceNotFound_thenThrowsException() {
        LocalDateTime now = LocalDateTime.now();
        PriceRequest request = new PriceRequest(now, 99L, 1L);

        when(priceRepository.findByBrandIdAndProductIdAndDate(anyLong(), anyLong(), any()))
                .thenReturn(Optional.empty());

        assertThrows(PriceNotFoundException.class, () -> useCase.execute(request));
    }

}
