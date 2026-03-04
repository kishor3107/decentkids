# Copilot Instructions — Decent Kids Wear Backend

## Project Overview
This is a **Spring Boot 3.2 + Java 17** REST API backend for the Decent Kids Wear
e-commerce platform (https://decentkids.dominal.in).
Repository: https://github.com/kishor3107/decentkids
Database: **PostgreSQL 15 running in Docker**
Auth: **JWT (jjwt 0.11.5)**
Build: **Maven**

---

## Repo Structure
```
decentkids/
├── frontend/          HTML pages (admin, cart, customer, index, products, profile)
├── backend/           Spring Boot Maven project
│   ├── src/
│   ├── pom.xml
│   └── README.md
├── docker-compose.yml PostgreSQL + pgAdmin via Docker
├── .github/
│   └── copilot-instructions.md
├── .gitignore
└── README.md
```

---

## Docker — PostgreSQL Setup

The project uses Docker Compose to run PostgreSQL locally. No local PostgreSQL install needed.

### docker-compose.yml services:
| Service | Container | Port | Credentials |
|---------|-----------|------|-------------|
| PostgreSQL 15 | decentkids_postgres | 5432 | postgres / decentkids@123 |
| pgAdmin 4 | decentkids_pgadmin | 5050 | admin@decentkids.com / admin123 |

### Key Docker commands (memorize these):
```bash
# Start the database
docker compose up -d

# Stop the database
docker compose down

# Check if postgres is healthy
docker compose ps

# View postgres logs
docker compose logs postgres

# Full reset (wipes all data)
docker compose down -v
```

### application.properties DB config (already set):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/decentkids_db
spring.datasource.username=postgres
spring.datasource.password=decentkids@123
```

---

## Package Structure
```
com.decentkids
├── config/       SecurityConfig, OpenApiConfig, DataSeeder
├── controller/   AuthController, ProductController, OrderController, AdminController
├── dto/          ApiResponse, AuthDto, ProductOrderDto
├── entity/       Customer, Admin, Product, Order, OrderItem
├── exception/    GlobalExceptionHandler + custom exceptions
├── repository/   JPA repositories
├── security/     JwtTokenProvider, JwtAuthenticationFilter
└── service/      AuthService, ProductService, OrderService
```

---

## Coding Standards — Always Follow

- Use **Lombok** — never write boilerplate getters/setters
- All controllers return `ApiResponse<T>`: `{ success, message, data }`
- Use `@Valid` on all `@RequestBody` parameters
- Services throw only: `ResourceNotFoundException`, `BadRequestException`, `UnauthorizedException`
- Never expose `password` field in any response DTO
- All DB writes must be `@Transactional`
- Use `BigDecimal` for all monetary values — never `double` or `float`
- Admin endpoints must have `@PreAuthorize("hasRole('ADMIN')")`
- No `@Autowired` field injection — always constructor injection via `@RequiredArgsConstructor`
- No `@CrossOrigin("*")` on controllers — CORS handled globally in `SecurityConfig`

---

## Validation Checklist Before Every Commit

### Security
- [ ] No real secrets committed — only `decentkids@123` placeholder for dev Docker
- [ ] Admin endpoints protected with `@PreAuthorize("hasRole('ADMIN')")`
- [ ] Passwords BCrypt-encoded before saving
- [ ] No `permitAll()` except `/auth/**`, GET `/products/**`, `/swagger-ui/**`

### Code Quality
- [ ] No unused imports, no missing imports
- [ ] `@Valid` on all `@RequestBody`
- [ ] `BigDecimal` for all prices
- [ ] `@Transactional` on all write operations
- [ ] Constructor injection only

### JPA
- [ ] `@OneToMany` uses `FetchType.LAZY`
- [ ] `@PrePersist` sets `createdAt`
- [ ] No N+1 query risk

### Git Hygiene
- [ ] `target/` not staged
- [ ] No `.class` files staged
- [ ] `.gitignore` is present and correct
- [ ] `docker-compose.yml` IS committed (it's safe — uses dev credentials)

---

## Commit Message Format
```
<type>(scope): <description>
```
Types: `feat`, `fix`, `refactor`, `docs`, `chore`
Scopes: `backend`, `auth`, `products`, `orders`, `admin`, `config`, `docker`

---

## What NOT to Do
- Do NOT use `HttpSession` — stateless JWT only
- Do NOT use `@Autowired` field injection
- Do NOT write raw SQL unless necessary
- Do NOT commit `target/` or `.class` files
- Do NOT use `float`/`double` for prices