package com.ecomerce.pricing.infrastructure.adapters.in.rest;

import com.ecomerce.pricing.application.dtos.PriceRequest;
import com.ecomerce.pricing.application.dtos.PriceResponse;
import com.ecomerce.pricing.application.ports.in.GetPriceUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final GetPriceUseCase getPriceUseCase;

    @GetMapping
    public PriceResponse getPrice(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
            @RequestParam Long productId,
            @RequestParam Long brandId) {

        PriceRequest request = new PriceRequest(applicationDate, productId, brandId);
        return getPriceUseCase.execute(request);
    }
}