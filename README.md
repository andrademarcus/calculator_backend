# Backend: Arithmetic Calculator REST API (Marcus Andrade)

This project handle all operations executed by project calculator_frontend

* Spring Boot 2.7
* Java 11
* JWT

## Backend
## Run application
```
./gradlew bootRun
```
The Spring Boot Server run at port `8080`.

After run, see API documentation at http://localhost:8080/docs/swagger-ui
Each API request requires a JWT token, given by /api/auth/signin


## User to generate a token and access resources 

User: marcusandrade816@gmail.com
Password: Myself00

## Run tests
```
./gradlew test
```

## Build executable JAR
```
./gradlew bootJar
```
