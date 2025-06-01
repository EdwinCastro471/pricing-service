package com.ecomerce.pricing;

import com.ecomerce.pricing.infrastructure.adapters.out.persistence.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class PricingRepositoryTests {

	@Mock
	private PriceRepository priceRepository;

	@Test
	public void testProductNotFound() {
		when(priceRepository.findById(anyLong())).thenReturn(Optional.empty());
	}
}
