# Product API - Proof of Concept (POC)

## Overview
This is a **Spring Boot 3** application built as a **Proof of Concept (POC)** for a **JWT-secured REST API**.  
It provides basic authentication and allows access to product-related endpoints.

## Technologies Used
- **Java 17**
- **Gradle**
- **Spring Boot 3**
- **Spring MVC**
- **Spring Security (JWT Authentication)**
- **Spring Data JPA**
- **PostgreSQL**
- **Flyway for database migrations**
- **Docker & Docker Compose** (for PostgreSQL setup)

## Getting Started

### 1 Start the PostgreSQL Database**
The application uses **Docker Compose** to spin up a PostgreSQL instance.  
To start up docker-compose you must have locally docker and docker-compose.
On Windows Docker Desktop or Rancher Desktop can be used.
When you're in root project directory run the following command to start the database:
```sh
cd environment
docker-compose up -d
```
### 2 Start the Application
By default, the application runs with the dev profile for easier setup.
Start the application using Gradle:
```sh
cd ..
./gradlew bootRun
```

### 3 Verify Swagger API Documentation
Once the application is running, you can access Swagger UI at:
```sh
http://localhost:8080/swagger-ui/index.html
```

You can use Swagger for further testing, alternately you could use PostMan.


## Authentication (JWT)

The API requires JWT authentication to access product-related endpoints.
Login Credentials

Use the following credentials to obtain a JWT token:

    Username: user
    Password: test

## Additional Notes

- The database schema is automatically created using Flyway migrations.
- User registration is not fully implemented since this is a simple POC.
- The application is configured for easy startup with Docker Compose and Gradle.
- The application has dev profile set as default in Gradle for easier setting up and testing.
