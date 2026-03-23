# Party Planner Backend (GEMINI.md)

This project is a Spring Boot-based REST API for a Party Planner application. It manages users, events, and tasks associated with those events.

## Project Overview

*   **Main Technologies:**
    *   **Java 17** with **Spring Boot 3.3.5**.
    *   **MongoDB** for data persistence (via `spring-boot-starter-data-mongodb`).
    *   **Spring Security** for authentication and CORS management.
    *   **JWT (JSON Web Tokens)** for stateless authentication, stored in **HTTP-only Cookies**.
    *   **Lombok** for reducing boilerplate code.
    *   **MapStruct** (or manual mappers in `mapper/` package) for DTO-to-Model conversion.
    *   **Docker** for containerized infrastructure (MongoDB).

*   **Architecture:**
    *   **Controllers:** REST endpoints in `org.example.backend.controller`.
    *   **Services:** Business logic in `org.example.backend.service`.
    *   **Repositories:** Data access in `org.example.backend.repository`.
    *   **Models:** Data entities in `org.example.backend.models`.
    *   **DTOs:** Data transfer objects in `org.example.backend.dto`.
    *   **Mappers:** Utilities for mapping between DTOs and Models in `org.example.backend.mapper`.

## Building and Running

*   **Prerequisites:**
    *   Java 17 installed.
    *   Docker and Docker Compose (for MongoDB).

*   **Key Commands:**
    *   **Start Infrastructure:** `docker-compose up -d` (starts MongoDB).
    *   **Build Project:** `./gradlew build`
    *   **Run Application:** `./gradlew bootRun`
    *   **Run Tests:** `./gradlew test`
    *   **Clean Build:** `./gradlew clean build`

*   **Configuration:**
    *   Application properties (likely in `src/main/resources/application.properties` or similar) contain configuration for MongoDB and JWT secrets.
    *   `tokenSecret` is required for JWT signing (defined in `JwtService`).

## Development Conventions

*   **Authentication:** 
    *   Authentication is handled via JWT. Upon successful login, a `token` cookie is set in the response.
    *   Endpoints verify the user by reading the `token` cookie (see `UserController.getLoggedUser`).
    *   Passwords are encrypted using **BCrypt** with a salt factor of 10.
*   **Error Handling:**
    *   Custom exceptions like `NotFoundException`, `ConflictException`, and `BadReqException` are used.
    *   A `ControllerAdvice` handles these exceptions and returns appropriate HTTP status codes.
*   **CORS:**
    *   Configured to allow requests from `http://localhost:3000` with credentials enabled.
*   **Testing:**
    *   JUnit 5 is used for testing.
    *   Spring Security Test is available for testing secured endpoints.

## Key Files

*   `src/main/java/org/example/backend/BackendApplication.java`: Entry point.
*   `src/main/java/org/example/backend/configs/SecurityConfig.java`: Security and CORS configuration.
*   `src/main/java/org/example/backend/service/JwtService.java`: JWT generation and parsing logic.
*   `docker-compose.yml`: Defines the MongoDB container.
*   `build.gradle`: Project dependencies and build configuration.
