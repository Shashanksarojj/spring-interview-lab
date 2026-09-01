# spring-interview-lab

A hands-on Spring Boot project for practicing the Java and Spring
concepts most commonly asked about in backend interviews: streams and
functional interfaces, JPA relationships, JPQL/native queries,
authentication, multithreading, testing, and design patterns.

All application code is written by hand by the project owner, working
through the checkpoints in
[`docs/superpowers/specs/2026-09-01-spring-interview-lab-design.md`](docs/superpowers/specs/2026-09-01-spring-interview-lab-design.md).
Current status of each checkpoint is tracked in [`CLAUDE.md`](CLAUDE.md).

## Stack

- Java 17, Spring Boot 4.1.0
- Spring Web MVC, Spring Data JPA, Spring Security, Bean Validation
- H2 (in-memory)
- Lombok
- JUnit 5, Mockito

## Domain

An e-commerce domain (Customer, Address, Category, Product, Order,
OrderItem, Payment, Review) chosen to cover every common JPA
relationship type and support realistic JPQL/native query practice.

## Running locally

```bash
./mvnw spring-boot:run
```

App runs on port `8088`. H2 console is available at `/h2-console`
(JDBC URL: `jdbc:h2:mem:interviewlabdb`).

## Running tests

```bash
./mvnw test
```
