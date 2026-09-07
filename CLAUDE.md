# spring-interview-lab — Project Status

Living tracker of what's done vs. pending. Updated after each
checkpoint. Full design/curriculum: `docs/superpowers/specs/2026-09-01-spring-interview-lab-design.md`.

## How this project works

Owner writes all application code by hand. Claude acts as learning
assistant: explains the concept for the current checkpoint, scopes one
task at a time, reviews code after it's written (read-only — does not
edit application code), and keeps this file + `README.md` in sync
after each checkpoint. Claude does not proactively write feature code.

## Checkpoints

- [x] **0. Project setup** — renamed to `spring-interview-lab`
      (groupId `com.dwivedicomms`), removed the original Book CRUD
      example entirely, reduced to a bare Spring Boot skeleton, git
      repo initialized.
- [x] **1. Domain foundation** — Customer/Address/Category/Product
      entities + repositories with derived query methods + seed data.
      `DataSeeder` (CommandLineRunner) was written by Claude at the
      owner's explicit request, as non-domain boilerplate — an
      exception to the "owner writes application code" rule below.
- [x] **2. Order domain** — Order/OrderItems/Payment entities
      (cascades, fetch strategies, `@Table(name = "orders")` override
      since `order` is a reserved SQL keyword). Repositories
      (OrderRepository/OrderItemsRepository/PaymentRepository) and the
      `DataSeeder` extension (two seeded orders with items + payments)
      were written by Claude as one-off exceptions to the "owner
      writes application code" rule, at the owner's explicit request —
      same basis as the Checkpoint 1 `DataSeeder` exception.
- [x] **3. JPQL & native queries** — N+1 problem demonstrated and
      fixed via `JOIN FETCH` (`OrderRepository`); join across
      Category's self-referencing tree (`ProductRepository`);
      WHERE-clause subquery (products priced above average, JPQL +
      native SQL side by side); `GROUP BY`/`HAVING` (category revenue,
      both as raw `Object[]` and as a `CategoryRevenue` DTO projection
      via JPQL constructor expression); pagination + sorting via
      `Pageable`/`Page<T>`. Also fixed two config/setup bugs found
      along the way: `application.yaml` had `show-sql` nested under
      `spring.jpa.hibernate` instead of `spring.jpa` (silently
      disabled SQL logging), and `ProductRepository` briefly imported
      `java.awt.print.Pageable` instead of
      `org.springframework.data.domain.Pageable`.
- [x] **4. Applied Java 8** — new `service` package. `OrderService`:
      `Optional.map`/`.orElseThrow` chained onto `findById`, a
      `Stream.map`/`.reduce` pipeline with a `BigDecimal::add` method
      reference, `@Transactional(readOnly = true)` to keep the lazy
      `orderItemsList` accessible across the chain. `CustomerService`:
      `Stream.map(Customer::getEmail).toList()`, and
      `Optional.map(Customer::getPhone).filter(Objects::nonNull)
      .orElse(0L)`. Two real bugs caught along the way: a missing
      `@Transactional` boundary caused a `LazyInitializationException`
      on `orderItemsList` (same class of issue as the Checkpoint 3 N+1
      case, but in real service code this time), and
      `jakarta.transaction.Transactional` was used instead of Spring's
      `org.springframework.transaction.annotation.Transactional` —
      the JTA version has no `readOnly` attribute, so it failed to
      compile once that attribute was added.
- [x] **5. Java 8 standalone lab** — new `java8lab` package, no
      Spring/JPA dependency, each concept a runnable `main`. Custom
      functional interface (`Discount`, `@FunctionalInterface`) with
      lambda implementations (`DiscountDemo`). `Collectors.groupingBy`
      with downstream `Collectors.mapping` and `Collectors.reducing`
      (`Item` record + `CollectorsDemo`). `flatMap` flattening nested
      `LabOrder`/`Item` lists into one stream (`FlatMapDemo`). The
      3-arg `reduce(identity, accumulator, combiner)` form, run both
      sequentially and via `parallelStream()` to show the combiner
      only fires when the stream is actually split across threads
      (`ReduceDemo`).
- [ ] **6. Testing** — unit, slice, and integration tests
- [ ] **7. Session-based auth** — DB-backed users, BCrypt, Spring
      Security from scratch
- [ ] **8. JWT auth** — login endpoint, JWT filter, method security
- [ ] **9. Multithreading** — concurrency lab + applied `@Async`/locking
- [ ] **10. Design patterns** — Strategy/Factory/Builder/Observer
      applied to real code

## Current structure

```
src/main/java/com/dwivedicomms/springinterviewlab/
  SpringInterviewLabApplication.java
  domain/
    Customer.java, Address.java, Category.java, Product.java,
    Order.java, OrderItems.java, Payment.java
  enums/
    StatusEnum.java, PaymentMethodEnum.java, PaymentStatusEnum.java
  repositories/
    CustomerRepository.java, AddressRepository.java,
    CategoryRepository.java, ProductRepository.java,
    OrderRepository.java, OrderItemsRepository.java,
    PaymentRepository.java
  dto/
    CategoryRevenue.java   (record, JPQL constructor-expression target)
  service/
    OrderService.java, CustomerService.java
  java8lab/
    Discount.java, DiscountDemo.java, Item.java, CollectorsDemo.java,
    LabOrder.java, FlatMapDemo.java, ReduceDemo.java
  config/
    DataSeeder.java   (CommandLineRunner, seeds dev data on startup)
src/main/resources/application.yaml   (H2 in-memory, port 8088)
src/test/java/com/dwivedicomms/springinterviewlab/
  SpringInterviewLabApplicationTests.java
```

No security config or exception handling exist yet — those start at
Checkpoints 7-8. Review is not yet modeled — that's a later checkpoint.
