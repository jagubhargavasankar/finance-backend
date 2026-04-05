# 💰 Finance Backend API

A secure and scalable backend system for managing financial records, built using **Spring Boot**.
This project includes authentication, role-based access control, transaction management, and dashboard analytics.

---

## 🚀 Features

* 🔐 JWT Authentication (Login/Register)
* 👥 User & Role Management (ADMIN, USER)
* 💸 Financial Records CRUD (Income/Expense tracking)
* 🔍 Record Filtering (by date, type, category)
* 📊 Dashboard APIs (totals, summaries, trends)
* 🛡️ Role-Based Access Control (RBAC)
* ✅ Input Validation & Exception Handling
* 🗄️ Data Persistence using PostgreSQL

---

## 🏗️ Tech Stack

* **Backend:** Spring Boot (Java)
* **Security:** Spring Security + JWT
* **Database:** PostgreSQL
* **ORM:** Spring Data JPA (Hibernate)
* **Build Tool:** Maven
* **Testing Tool:** Postman

---

## 📁 Project Structure

```
src/main/java/com/finance/finance_backend/
│
├── controller/        # REST Controllers (API endpoints)
├── service/           # Business logic layer
├── repository/        # Database access layer
├── entity/            # Database entities
├── dto/               # Request & Response objects
├── security/          # JWT & authentication logic
├── config/            # Security configuration
└── enums/             # Enum classes (Role, Transaction Type)
```

---

## 🔐 Authentication Flow

1. User registers via `/api/auth/register`
2. User logs in via `/api/auth/login`
3. JWT token is generated
4. Token must be sent in headers:

```
Authorization: Bearer <token>
```

5. Protected APIs validate token before access

---

## 📊 API Endpoints (Sample)

### 🔑 Auth APIs

* `POST /api/auth/register`
* `POST /api/auth/login`

### 👤 User APIs

* `GET /api/users`
* `POST /api/users`

### 💰 Transaction APIs

* `POST /api/transactions`
* `GET /api/transactions`
* `PUT /api/transactions/{id}`
* `DELETE /api/transactions/{id}`

### 📈 Dashboard APIs

* `GET /api/dashboard/summary`
* `GET /api/dashboard/trends`

---

## ⚙️ Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone https://github.com/your-username/finance-backend.git
cd finance-backend
```

### 2️⃣ Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/finance_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3️⃣ Run Application

```bash
mvn spring-boot:run
```

---

## 🧪 Testing APIs

Use **Postman** to test endpoints:

* Base URL: `http://localhost:8080`
* Add JWT token in headers for protected APIs

---

## ⚖️ Technical Decisions

* Used **Spring Boot** for rapid development and scalability
* Implemented **JWT authentication** for stateless security
* Used **RBAC** for fine-grained authorization
* Adopted **Layered Architecture** for clean code structure
* Used **JPA/Hibernate** to reduce boilerplate database code

---

## ⚠️ Trade-offs

* JWT adds complexity in token handling
* JPA abstracts SQL but reduces low-level control
* No deployment yet (runs locally)

---

## 🚀 Future Improvements

* Add Swagger API Documentation
* Deploy to cloud (Render / AWS)
* Add unit & integration tests
* Implement pagination & sorting
* Add frontend (React / Next.js)

---

## 👨‍💻 Author

**Bhargava**
Backend Developer (Java | Spring Boot | REST APIs | MySQL/PostgreSQL)

---

## 📌 Conclusion

This project demonstrates a **real-world backend system** with secure authentication, clean architecture, and scalable design.
It is suitable for financial data management and can be extended into a full-stack production application.
