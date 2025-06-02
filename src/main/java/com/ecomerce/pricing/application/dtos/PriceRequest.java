package com.ecomerce.pricing.application.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceRequest {
    private LocalDateTime applicationDate;
    private Long productId;
    private Long brandId;
}