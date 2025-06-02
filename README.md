# Pricing Service

A microservice designed to manage product pricing within an e-commerce system, 
based on defining applicable rates within specific date ranges. 
Developed with **Java 21 Spring Boot**, following a **Hexagonal Architecture**.

## Features

- Retrieval of final prices (PVP) by product, brand, and application date.
- Automatic prioritization of overlapping rates.
- Input parameter validation.
- Decoupled model using MapStruct.
- In-memory H2 database (for development).

## Technology Stack
- **Java 21** + **Spring Boot 3.5.0**
- **Spring Data JPA** (Persistence)
- **(Development)** / **MySQL** (Production)
- **MapStruct** (Object Mapping)
- **Lombok** (Boilerplate Reduction)
- **JUnit 5** + **Mockito** (Testing)


## 🚀 How Launch

### Requirements
- Java 21
- Gradle

### Steps
**Repository Clone**:
```bash
git clone https://github.com/EdwinCastro471/pricing-service.git
```
**Run**:
```bash
cd pricing-service
./gradlew bootRun
```
## API Usage
### Endpoint
```http
GET /api/prices?applicationDate=2020-06-14T16:00:00&brandId=1&productId=35455
```
The application will start on 'http://localhost:8081'

### Example Response
```json
{
    "productId": 35455,
    "brandId": 1,
    "priceList": 2,
    "startDate": "2020-06-14T15:00:00",
    "endDate": "2020-06-14T18:30:00",
    "finalPrice": 25.45,
    "currency": "EUR"
}
```

## Author
Made ️by [Edwin Castro](https://github.com/EdwinCastro471)