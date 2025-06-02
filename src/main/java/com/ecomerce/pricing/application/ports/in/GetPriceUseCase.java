package com.ecomerce.pricing.application.ports.in;

import com.ecomerce.pricing.application.dtos.PriceRequest;
import com.ecomerce.pricing.application.dtos.PriceResponse;

public interface GetPriceUseCase {
    PriceResponse execute(PriceRequest request);
}
