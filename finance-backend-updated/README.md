# Finance Data Processing and Access Control Backend

A Spring Boot REST API backend for a finance dashboard system with role-based access control, JWT authentication, and financial record management.

---

## Tech Stack

| Layer        | Technology                     |
|--------------|-------------------------------|
| Language     | Java 17                        |
| Framework    | Spring Boot 3.x                |
| Security     | Spring Security + JWT (jjwt)   |
| Database     | PostgreSQL                     |
| ORM          | Spring Data JPA (Hibernate)    |
| Validation   | Jakarta Bean Validation        |
| Build tool   | Maven                          |
| Utilities    | Lombok                         |

---

## Setup & Run

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL running locally

### Database setup
```sql
CREATE DATABASE finance_db;
```

### Configuration (`src/main/resources/application.properties`)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```
Tables are auto-created on first run via `ddl-auto=update`.

### Run
```bash
mvn spring-boot:run
```
Server starts at `http://localhost:8080`

---

## Roles & Permissions

| Action                        | VIEWER | ANALYST | ADMIN |
|-------------------------------|--------|---------|-------|
| Register / Login              | ✅     | ✅      | ✅    |
| View own transactions         | ✅     | ✅      | ✅    |
| Filter transactions           | ✅     | ✅      | ✅    |
| Create / Update / Delete tx   | ❌     | ❌      | ✅    |
| View dashboard summary        | ❌     | ✅      | ✅    |
| View category-wise / trends   | ✅     | ✅      | ✅    |
| Manage users (CRUD)           | ❌     | ❌      | ✅    |

---

## API Reference

All protected endpoints require:
```
Authorization: Bearer <token>
```

### Auth

| Method | Endpoint         | Access | Description          |
|--------|------------------|--------|----------------------|
| POST   | /auth/register   | Public | Register a new user  |
| POST   | /auth/login      | Public | Login, get JWT token |

**Register request body:**
```json
{
  "name": "Jagu Bhargava",
  "email": "jagu@example.com",
  "password": "secret123",
  "role": "ADMIN"
}
```

**Login request body:**
```json
{
  "email": "jagu@example.com",
  "password": "secret123"
}
```

---

### Transactions

| Method | Endpoint              | Access              | Description                        |
|--------|-----------------------|---------------------|------------------------------------|
| POST   | /transactions         | ADMIN               | Create a transaction               |
| GET    | /transactions         | VIEWER/ANALYST/ADMIN| List own transactions (paginated)  |
| PUT    | /transactions/{id}    | ADMIN               | Update a transaction               |
| DELETE | /transactions/{id}    | ADMIN               | Delete a transaction               |

**GET /transactions query params:**

| Param     | Type   | Example      | Description               |
|-----------|--------|--------------|---------------------------|
| type      | String | INCOME       | Filter by type            |
| category  | String | Salary       | Filter by category        |
| startDate | String | 2024-01-01   | Filter from date          |
| endDate   | String | 2024-12-31   | Filter to date            |
| page      | int    | 0            | Page number (default: 0)  |
| size      | int    | 10           | Page size (default: 10)   |

**Transaction request body:**
```json
{
  "amount": 5000.00,
  "type": "INCOME",
  "category": "Salary",
  "note": "Monthly salary",
  "date": "2024-01-31"
}
```

---

### Dashboard

| Method | Endpoint                  | Access              | Description                          |
|--------|---------------------------|---------------------|--------------------------------------|
| GET    | /dashboard/summary        | ANALYST/ADMIN       | Income, expense, net balance, recent |
| GET    | /dashboard/category-wise  | All authenticated   | Totals grouped by category           |
| GET    | /dashboard/trends         | All authenticated   | Monthly totals (e.g. "2024-01")      |

**Summary response:**
```json
{
  "totalIncome": 15000.0,
  "totalExpense": 6500.0,
  "netBalance": 8500.0,
  "recentTransactions": [ ... ]
}
```

---

### Users (ADMIN only)

| Method | Endpoint       | Description              |
|--------|----------------|--------------------------|
| POST   | /users         | Create a user            |
| GET    | /users         | List all users           |
| GET    | /users/{id}    | Get user by ID           |
| PUT    | /users/{id}    | Update user (role/status)|
| DELETE | /users/{id}    | Delete a user            |

> Passwords are never returned in any user response.

---

## Error Responses

All errors return a structured body:
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "message": "Transaction not found with id: 42"
}
```

| Status | Meaning                        |
|--------|--------------------------------|
| 400    | Validation error / bad input   |
| 401    | Invalid credentials / inactive |
| 403    | Insufficient role              |
| 404    | Resource not found             |
| 500    | Internal server error          |

---

## Assumptions

1. **Any user can register with any role** — in a real system, only an existing ADMIN would be able to create ADMIN accounts. For this assessment, registration is open to simplify testing.
2. **Transactions are user-scoped** — each user can only view and modify their own transactions. Admins do not see other users' transactions through the transactions API.
3. **Soft delete was not implemented** — the assignment listed it as optional. Hard delete is used. A `deletedAt` column could be added with minimal changes if needed.
4. **Date filter uses ISO format** (`yyyy-MM-dd`) for simplicity and consistency with `LocalDate`.
5. **JWT secret is hardcoded** — in production this should be externalized via environment variable or secrets manager.

---

## Project Structure

```
src/main/java/com/finance/finance_backend/
├── controller/        # REST endpoints
├── service/           # Business logic
├── repository/        # JPA data access
├── entity/            # JPA entities
├── dto/
│   ├── request/       # Input DTOs
│   └── response/      # Output DTOs (no password leakage)
├── enums/             # RoleName, TransactionType
├── security/          # JWT filter, provider, UserDetailsService
├── config/            # SecurityConfig
└── exception/         # Custom exceptions + global handler
```
