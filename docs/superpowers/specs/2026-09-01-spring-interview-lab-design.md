# spring-interview-lab — Design & Curriculum

Date: 2026-09-01

## Purpose

A hands-on Spring Boot + Java project for practicing the concepts most
commonly asked about in Java/Spring backend interviews: Java 8
functional programming and streams, JPA relationships and JPQL/native
queries, authentication, multithreading/concurrency, testing, and
common design patterns.

The owner writes all application code by hand. Claude's role is
learning assistant: explain concepts, scope one task at a time, review
code after it's written, and keep the project's status docs
(`README.md`, `CLAUDE.md`) up to date. Claude does not write feature
code unless explicitly asked to (as with this initial project setup).

## Starting point

The project began life as a Spring Initializr skeleton with a
throwaway Book CRUD example (entity/repository/service/controller,
basic HTTP Basic security, global exception handling). That example
has been fully removed. As of this design, the project is a **bare
Spring Boot skeleton**:

- `com.dwivedicomms.springinterviewlab.SpringInterviewLabApplication` — main class
- `SpringInterviewLabApplicationTests` — default context-load test
- `application.yaml` — H2 in-memory datasource, JPA, H2 console enabled
- `pom.xml` — groupId `com.dwivedicomms`, artifactId `spring-interview-lab`

Dependencies already present: Spring Web MVC, Spring Data JPA, Spring
Security, Bean Validation, H2, Lombok, DevTools, plus the corresponding
test starters. No security config, no exception handling, no domain
classes exist yet — all of that is built from scratch during the
checkpoints below, including auth (previously drafted with HTTP Basic
+ in-memory users; that scaffolding was intentionally discarded so
auth is built properly at Checkpoints 7-8 instead).

## Domain model

A fresh e-commerce domain, chosen to exercise every relationship type
interviewers ask about:

| Entity | Relationships | Concepts it unlocks |
|---|---|---|
| `Customer` | — | aggregate root |
| `Address` | `@OneToOne`/`@OneToMany` → Customer | embedding vs. separate table |
| `Category` | self-referencing `@ManyToOne` (parent category) | recursive/tree queries |
| `Product` | `@ManyToOne` → Category | basic FK join |
| `Order` | `@ManyToOne` → Customer, `@OneToMany` → OrderItem | cascade, orphanRemoval |
| `OrderItem` | `@ManyToOne` → Order, `@ManyToOne` → Product | join entity = Many-to-Many(Order, Product) |
| `Payment` | `@OneToOne` → Order | one-to-one practice |
| `Review` | `@ManyToOne` → Product, `@ManyToOne` → Customer | aggregate queries (avg rating, GROUP BY/HAVING) |

This naturally produces interview-style queries: "customers who spent
more than average," "top-rated products per category," N+1 detection
on Order→OrderItem→Product traversals, etc.

## Checkpoint roadmap

Each checkpoint is a hands-on unit: concept explanation → scoped task
→ owner implements it → review → discussion → next checkpoint.

0. **Project setup** (done) — rename to spring-interview-lab, remove
   Book example, bare skeleton, git repo
1. **Domain foundation** — Customer/Address/Category/Product entities,
   repositories with derived query methods, seed data
2. **Order domain** — Order/OrderItem/Payment; cascade types, fetch
   strategies
3. **JPQL & native queries** — joins, nested subqueries, GROUP
   BY/HAVING, DTO projections, pagination/sorting, N+1 problem +
   `JOIN FETCH`/`@EntityGraph`
4. **Applied Java 8** — refactor services with Streams/Optional/method
   references
5. **Java 8 standalone lab** — separate package: custom functional
   interfaces, Collectors, flatMap/reduce, Optional chaining
6. **Testing** — JUnit5+Mockito unit tests, `@DataJpaTest`/`@WebMvcTest`
   slice tests, `@SpringBootTest` integration tests covering the
   queries from Checkpoint 3
7. **Session-based auth** — DB-backed Users table, BCrypt, roles from
   real data, Spring Security filter chain built from scratch
8. **JWT auth** — login endpoint, JWT filter, `@PreAuthorize`
   method security
9. **Multithreading** — standalone concurrency lab (ExecutorService,
   CompletableFuture, locks, producer-consumer) + applied (`@Async`,
   optimistic/pessimistic locking for stock deduction)
10. **Design patterns** — Strategy (payment methods), Factory
    (discounts), Builder, Observer (order status events) — applied
    against real code in this project, not toy examples

## Database

H2 in-memory for now (zero setup, fast iteration on JPA/JPQL
concepts). Migration to PostgreSQL via Docker is a future option once
the schema and queries are in place, if real-SQL-feature practice
(window functions, EXPLAIN ANALYZE) becomes useful.

## Status tracking

`CLAUDE.md` at the project root is the living status tracker — updated
after each checkpoint to reflect what's done vs. pending. `README.md`
covers onboarding (what the project is, stack, how to run).
