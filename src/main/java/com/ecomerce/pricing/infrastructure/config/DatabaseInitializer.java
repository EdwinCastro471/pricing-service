package com.ecomerce.pricing.infrastructure.config;

import com.ecomerce.pricing.domain.exceptions.DataInitializationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private static final String CSV_FILE_PATH = "classpath:data/prices.csv";
    private static final String INSERT_SQL = """
            INSERT INTO PRICES (
                BRAND_ID, START_DATE, END_DATE, PRICE_LIST, 
                PRODUCT_ID, PRIORITY, PRICE, CURRENCY, 
                LAST_UPDATE, LAST_UPDATE_BY
            )
            SELECT 
                CAST(BrandId AS BIGINT),
                PARSEDATETIME(StartDate, 'yyyy-MM-dd-HH.mm.ss'),
                PARSEDATETIME(EndDate, 'yyyy-MM-dd-HH.mm.ss'),
                CAST(PriceList AS INTEGER),
                CAST(ProductId AS BIGINT),
                CAST(Priority AS INTEGER),
                CAST(Price AS DECIMAL(10,2)),
                Currency,
                PARSEDATETIME(LastUpdate, 'yyyy-MM-dd-HH.mm.ss'),
                LastUpdateBy
            FROM CSVREAD('classpath:data/prices.csv', null, 'fieldSeparator=,')
        """;

    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            log.info("Initializer upload data from {}", CSV_FILE_PATH);
            int rowsInserted = executeInsertFromCsv();
            log.info("Completed upload data. {} field inserted.", rowsInserted);
        } catch (DataAccessException e) {
            log.error("Data access error in upload data from CSV.", e);
            throw new DataInitializationException("Error initializing data from CSV.", e);
        } catch (Exception e) {
            log.error("Unexpected error initializing data.", e);
            throw new DataInitializationException("Unexpected error initializing", e);
        }
    }

    private int executeInsertFromCsv() {
        return jdbcTemplate.update(INSERT_SQL);
    }
}