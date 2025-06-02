package com.ecomerce.pricing;

import com.ecomerce.pricing.application.dtos.PriceResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PriceIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void test1_requestAt_2020_06_14_10_00_shouldReturn_35_50() {
        testPrice("2020-06-14T10:00:00", 1, "35.50");
    }

    @Test
    void test2_requestAt_2020_06_14_16_00_shouldReturn_25_45() {
        testPrice("2020-06-14T16:00:00", 2, "25.45");
    }

    @Test
    void test3_requestAt_2020_06_14_21_00_shouldReturn_35_50() {
        testPrice("2020-06-14T21:00:00", 1, "35.50");
    }

    @Test
    void test4_requestAt_2020_06_15T10_00_shouldReturn_30_50() {
        testPrice("2020-06-15T10:00:00", 3, "30.50");
    }

    @Test
    void test5_requestAt_2020_06_16T21_00_shouldReturn_38_95() {
        testPrice("2020-06-16T21:00:00", 4, "38.95");
    }

    private void testPrice(String date, int expectedPriceList, String expectedPrice) {
        Long PRODUCT_ID = 35455L;
        Long brandId = 1L;
        String URL = "/api/prices";
        String requestUrl = URL + "?applicationDate=" + date + "&productId=" + PRODUCT_ID + "&brandId=" + brandId;

        ResponseEntity<PriceResponse> response = restTemplate.getForEntity(requestUrl, PriceResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        PriceResponse body = response.getBody();
        assertNotNull(body);
        assertEquals(PRODUCT_ID, body.getProductId());
        assertEquals(brandId, body.getBrandId());
        assertEquals(expectedPriceList, body.getPriceList());
        assertEquals(new BigDecimal(expectedPrice), body.getFinalPrice());
    }
}
