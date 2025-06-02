package com.ecomerce.pricing;

import com.ecomerce.pricing.application.dtos.PriceResponse;
import com.ecomerce.pricing.application.ports.in.GetPriceUseCase;
import com.ecomerce.pricing.domain.exceptions.PriceNotFoundException;
import com.ecomerce.pricing.infrastructure.adapters.in.rest.PriceController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PriceController.class)
@AutoConfigureMockMvc
class PriceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetPriceUseCase getPriceUseCase;

    private final static String BASE_URL = "/api/prices";

    @Test
    void whenValidRequest_thenReturnsPrice() throws Exception {
        PriceResponse mockResponse = PriceResponse.builder()
                .productId(35455L)
                .brandId(1L)
                .priceList(1)
                .startDate(LocalDateTime.parse("2020-06-14T00:00:00"))
                .endDate(LocalDateTime.parse("2020-12-31T23:59:59"))
                .finalPrice(BigDecimal.valueOf(35.50))
                .currency("EUR")
                .build();

        given(getPriceUseCase.execute(any())).willReturn(mockResponse);

        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(35455))
                .andExpect(jsonPath("$.finalPrice").value(35.50));
    }

    @Test
    void whenPriceNotFound_thenReturns404() throws Exception {
        given(getPriceUseCase.execute(any()))
                .willThrow(new PriceNotFoundException(35455L, 1L, LocalDateTime.now()));

        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound());
    }
}