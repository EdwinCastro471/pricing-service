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

## Technology
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
1. **Repository Clone**:
   ```bash
   git clone https://github.com/EdwinCastro471/pricing-service.git
   cd pricing-service