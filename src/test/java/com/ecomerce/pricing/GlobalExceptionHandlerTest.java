package com.ecomerce.pricing;

import com.ecomerce.pricing.application.ports.in.GetPriceUseCase;
import com.ecomerce.pricing.domain.exceptions.PriceNotFoundException;
import com.ecomerce.pricing.infrastructure.adapters.in.rest.GlobalExceptionHandler;
import com.ecomerce.pricing.infrastructure.adapters.in.rest.PriceController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    private MockMvc mockMvc;

    @Mock
    private GetPriceUseCase getPriceUseCase;

    @InjectMocks
    private PriceController priceController;

    private final String BASE_URL = "/api/prices";

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenPriceNotFound_thenReturns404() throws Exception {
        given(getPriceUseCase.execute(any())).willThrow(new PriceNotFoundException("Price not found"));

        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Price not found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void whenInvalidDate_thenReturns400() throws Exception {
        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "invalid-date")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    void whenUnexpectedException_thenReturns500() throws Exception {
        given(getPriceUseCase.execute(any())).willThrow(new RuntimeException("Unexpected"));

        mockMvc.perform(get(BASE_URL)
                        .param("applicationDate", "2020-06-14T10:00:00")
                        .param("productId", "35455")
                        .param("brandId", "1"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Internal server error. Please contact support."))
                .andExpect(jsonPath("$.error").value("Internal Server Error"));
    }
}