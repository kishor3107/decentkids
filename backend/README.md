# Decent Kids Wear — Java Spring Boot Backend

REST API backend for the [Decent Kids Wear](https://decentkids.dominal.in/) e-commerce platform.

---

## Tech Stack

| Layer | Technology |
|-------|-----------|
| Framework | Spring Boot 3.2 |
| Language | Java 17 |
| Database | PostgreSQL 15+ |
| Auth | JWT (jjwt 0.11.5) |
| ORM | Spring Data JPA / Hibernate |
| Docs | Swagger UI (SpringDoc OpenAPI) |
| Build | Maven |

---

## Project Structure

```
src/main/java/com/decentkids/
├── DecentKidsApplication.java       # Entry point
├── config/
│   ├── DataSeeder.java              # Creates default admin on startup
│   ├── OpenApiConfig.java           # Swagger config
│   └── SecurityConfig.java          # JWT + CORS + route protection
├── controller/
│   ├── AuthController.java          # /auth/**
│   ├── ProductController.java       # /products/**
│   ├── OrderController.java         # /orders/**
│   └── AdminController.java         # /admin/**
├── dto/
│   ├── ApiResponse.java             # Generic response wrapper
│   ├── AuthDto.java                 # Login/register request & response
│   └── ProductOrderDto.java         # Product & Order DTOs
├── entity/
│   ├── Customer.java
│   ├── Admin.java
│   ├── Product.java
│   ├── Order.java
│   └── OrderItem.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   ├── ResourceNotFoundException.java
│   ├── BadRequestException.java
│   └── UnauthorizedException.java
├── repository/
│   ├── CustomerRepository.java
│   ├── AdminRepository.java
│   ├── ProductRepository.java
│   └── OrderRepository.java
├── security/
│   ├── JwtTokenProvider.java
│   └── JwtAuthenticationFilter.java
└── service/
    ├── AuthService.java
    ├── ProductService.java
    └── OrderService.java
```

---

## Setup & Run

### 1. Prerequisites
- Java 17+
- PostgreSQL 15++
- Maven 3.8+

### 2. Database Setup

```sql
createdb decentkids_db
# or via psql:
# psql -U postgres -c "CREATE DATABASE decentkids_db;"
```

### 3. Configure `application.properties`

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:3306/decentkids_db?createDatabaseIfNotExist=true
spring.datasource.username=YOUR_MYSQL_USERNAME
spring.datasource.password=YOUR_MYSQL_PASSWORD

app.jwt.secret=your_32+_char_secret_key_here
```

### 4. Run

```bash
mvn spring-boot:run
```

App starts at: `http://localhost:8080/api`

---

## Default Admin Credentials

On first startup, a default admin is auto-created:

| Field | Value |
|-------|-------|
| Username | `admin` |
| Password | `Admin@123` |

> ⚠️ Change the password immediately after first login!

---

## API Endpoints

### Auth (`/api/auth`)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/auth/customer/register` | Register new customer | Public |
| POST | `/auth/customer/login` | Customer login | Public |
| POST | `/auth/admin/login` | Admin login | Public |

### Products (`/api/products`)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| GET | `/products` | Get all products (paged) | Public |
| GET | `/products/{id}` | Get product by ID | Public |
| GET | `/products/search?query=...` | Search products | Public |
| GET | `/products/category/{category}` | Filter by category | Public |
| GET | `/products/age-group/{ageGroup}` | Filter by age group | Public |
| POST | `/products` | Create product | Admin |
| PUT | `/products/{id}` | Update product | Admin |
| DELETE | `/products/{id}` | Soft-delete product | Admin |

**Categories:** `TOPS, BOTTOMS, DRESSES, SETS, ACCESSORIES, FOOTWEAR, WINTERWEAR, SCHOOLWEAR`  
**Age Groups:** `INFANT_0_1, TODDLER_1_3, KIDS_3_8, TWEEN_8_14`

### Orders (`/api/orders`)

| Method | Endpoint | Description | Auth |
|--------|----------|-------------|------|
| POST | `/orders/customer/{customerId}` | Place order | Customer |
| GET | `/orders/customer/{customerId}` | Customer's orders | Customer |
| GET | `/orders/{orderId}` | Get order by ID | Customer/Admin |
| GET | `/orders` | All orders (paged) | Admin |
| PATCH | `/orders/{orderId}/status?status=SHIPPED` | Update order status | Admin |

**Order Statuses:** `PENDING, CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED`

### Admin (`/api/admin`)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/dashboard` | Stats, revenue, low stock |
| GET | `/admin/customers` | All customers |
| DELETE | `/admin/customers/{id}` | Deactivate customer |

---

## Swagger UI

Once running, visit:
```
http://localhost:8080/api/swagger-ui.html
```

---

## CORS

The backend allows requests from:
- `http://localhost:3000` (local dev)
- `https://decentkids.dominal.in` (production)

Add more origins in `application.properties`:
```properties
app.cors.allowed-origins=https://decentkids.dominal.in,http://localhost:3000
```

---

## Connecting the Frontend

In your frontend JS, use:

```js
const BASE_URL = "http://localhost:8080/api";

// Register
await fetch(`${BASE_URL}/auth/customer/register`, {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ fullName, email, password, phone, address, age, gender })
});

// Login
const res = await fetch(`${BASE_URL}/auth/customer/login`, {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  body: JSON.stringify({ email, password })
});
const { data: { token } } = await res.json();

// Authenticated request
await fetch(`${BASE_URL}/orders/customer/1`, {
  headers: { Authorization: `Bearer ${token}` }
});
```
