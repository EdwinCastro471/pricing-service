package com.ecomerce.pricing.domain.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Price {
    private Long id;
    private Long brandId;
    private Long productId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer priceList;
    private BigDecimal price;
    private String currency;
    private Integer priority;
    private LocalDateTime lastUpdate;
    private String lastUpdateBy;
}
