package com.ecomerce.pricing.application.usecases;

import com.ecomerce.pricing.application.dtos.PriceRequest;
import com.ecomerce.pricing.application.dtos.PriceResponse;
import com.ecomerce.pricing.application.ports.in.GetPriceUseCase;
import com.ecomerce.pricing.application.ports.out.PriceRepositoryPort;
import com.ecomerce.pricing.domain.exceptions.PriceNotFoundException;
import com.ecomerce.pricing.domain.models.Price;
import com.ecomerce.pricing.domain.services.PriceValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetPriceUseCaseImpl implements GetPriceUseCase {

    private final PriceRepositoryPort priceRepository;
    private final PriceValidator priceValidator;

    @Override
    public PriceResponse execute(PriceRequest request) {
        Price price = priceRepository
                .findByBrandIdAndProductIdAndDate(
                        request.getBrandId(),
                        request.getProductId(),
                        request.getApplicationDate()
                )
                .orElseThrow(() -> new PriceNotFoundException(
                        request.getProductId(),
                        request.getBrandId(),
                        request.getApplicationDate()
                ));



        priceValidator.validate(price, request.getApplicationDate());

        return PriceResponse.builder()
                .productId(price.getProductId())
                .brandId(price.getBrandId())
                .priceList(price.getPriceList())
                .startDate(price.getStartDate())
                .endDate(price.getEndDate())
                .finalPrice(price.getPrice())
                .currency(price.getCurrency())
                .build();
    }
}
