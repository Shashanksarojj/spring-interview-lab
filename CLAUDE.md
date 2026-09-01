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
- [ ] **1. Domain foundation** — Customer/Address/Category/Product
      entities + repositories + seed data
- [ ] **2. Order domain** — Order/OrderItem/Payment, cascades, fetch
      strategies
- [ ] **3. JPQL & native queries** — joins, subqueries, GROUP
      BY/HAVING, DTO projections, pagination, N+1 fixes
- [ ] **4. Applied Java 8** — Streams/Optional/method references in
      the service layer
- [ ] **5. Java 8 standalone lab** — functional interfaces,
      Collectors, flatMap/reduce
- [ ] **6. Testing** — unit, slice, and integration tests
- [ ] **7. Session-based auth** — DB-backed users, BCrypt, Spring
      Security from scratch
- [ ] **8. JWT auth** — login endpoint, JWT filter, method security
- [ ] **9. Multithreading** — concurrency lab + applied `@Async`/locking
- [ ] **10. Design patterns** — Strategy/Factory/Builder/Observer
      applied to real code

## Current skeleton

```
src/main/java/com/dwivedicomms/springinterviewlab/
  SpringInterviewLabApplication.java
src/main/resources/application.yaml   (H2 in-memory, port 8088)
src/test/java/com/dwivedicomms/springinterviewlab/
  SpringInterviewLabApplicationTests.java
```

No domain classes, security config, or exception handling exist yet —
all built from scratch starting at Checkpoint 1.
