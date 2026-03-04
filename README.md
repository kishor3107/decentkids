# Decent Kids Wear - Full Stack E-Commerce Platform

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue.svg)](https://www.postgresql.org/)
[![Docker](https://img.shields.io/badge/Docker-Supported-blue.svg)](https://www.docker.com/)

A modern, full-featured e-commerce platform for children's clothing, built with **Spring Boot 3.2**, **Java 17**, and **PostgreSQL 15**. Features include customer authentication, product catalog, shopping cart, order management, and comprehensive admin panel.

🌐 **Live Demo**: [decentkids.dominal.in](https://decentkids.dominal.in)  
📱 **Responsive Design**: Optimized for desktop, tablet, and mobile  
🔐 **JWT Authentication**: Secure role-based access control

---

## 📋 Table of Contents

- [🚀 Quick Start](#-quick-start)
- [🏗️ Architecture](#️-architecture)
- [✨ Features](#-features)
- [🛠️ Built With](#️-built-with)
- [📁 Project Structure](#-project-structure)
- [⚙️ Configuration](#️-configuration)
- [🔌 API Documentation](#-api-documentation)
- [🧪 Testing](#-testing)
- [🚢 Deployment](#-deployment)
- [👥 Contributing](#-contributing)

---

## 🚀 Quick Start

### Prerequisites

- **Java 17** or higher
- **Maven 3.6+**
- **Docker & Docker Compose**
- **Git**

### 1. Clone & Setup

```bash
git clone https://github.com/kishor3107/decentkids.git
cd decentkids

# Start PostgreSQL Database
docker compose up -d

# Build and run the application
cd backend
mvn clean install
mvn spring-boot:run
```

### 2. Access the Application

- **Frontend**: `http://localhost:8080` (served by Spring Boot)
- **API Base**: `http://localhost:8080/api`
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **Database (pgAdmin)**: `http://localhost:5050`

### 3. Default Credentials

| Role | Username | Password |
|------|----------|----------|
| **Admin** | `admin@decentkids.com` | `admin123` |
| **Customer** | `customer@example.com` | `customer123` |

*Note: Default admin credentials are seeded automatically on first run.*

---

## 🏗️ Architecture

```
┌─────────────────┬─────────────────┬─────────────────┐
│   Frontend      │   Backend API   │   Database      │
│   (HTML/JS)     │   (Spring Boot) │   (PostgreSQL)  │
├─────────────────┼─────────────────┼─────────────────┤
│ • Bootstrap 5   │ • Spring Boot 3 │ • PostgreSQL 15 │
│ • Vanilla JS    │ • Spring Data   │ • Running in    │
│ • JWT Storage   │ • JWT Security  │   Docker        │
│ • Fetch API     │ • REST APIs     │ • Auto-migrate  │
│ • Responsive    │ • OpenAPI/      │   schemas       │
│   Design        │   Swagger       │                 │
└─────────────────┴─────────────────┴─────────────────┘
```

### Key Design Patterns

- **Repository Pattern**: Clean data access layer
- **DTO Pattern**: Separate API contracts from entities
- **Global Exception Handling**: Consistent error responses
- **JWT Stateless Authentication**: Scalable security model
- **RESTful API Design**: Standard HTTP methods and status codes

---

## ✨ Features

### 👤 Customer Features
- ✅ **User Registration & Login** - Secure JWT-based authentication
- ✅ **Product Browsing** - Advanced filtering, search, and pagination
- ✅ **Shopping Cart** - Add/remove items, quantity management
- ✅ **Order Management** - Place orders, view history, track status
- ✅ **Profile Management** - Update personal information
- ✅ **Responsive Design** - Mobile-friendly interface

### 🔧 Admin Features
- ✅ **Admin Dashboard** - Key metrics and recent activity
- ✅ **Product Management** - CRUD operations, inventory tracking
- ✅ **Customer Management** - View all registered customers
- ✅ **Order Management** - View all orders, update status
- ✅ **Secure Access** - Admin-only protected routes

### 🎯 Technical Features
- ✅ **JWT Authentication** - Secure, stateless authentication
- ✅ **Role-based Authorization** - Customer and Admin roles
- ✅ **Data Validation** - Comprehensive input validation
- ✅ **Error Handling** - Consistent error responses
- ✅ **API Documentation** - Interactive Swagger UI
- ✅ **Database Migration** - Automatic schema management

---

## 🛠️ Built With

### Backend Technologies
- **Java 17** - Modern Java features
- **Spring Boot 3.2** - Auto-configuration and production-ready features
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Data persistence layer
- **JWT (jjwt 0.11.5)** - Secure token-based authentication
- **PostgreSQL 15** - Robust relational database
- **Maven** - Dependency management
- **Swagger/OpenAPI** - API documentation

### Frontend Technologies
- **HTML5 & CSS3** - Modern web standards
- **Bootstrap 5** - Responsive UI framework
- **Vanilla JavaScript** - No framework dependencies
- **Fetch API** - HTTP client for API calls
- **localStorage** - Client-side data persistence

### DevOps & Tools
- **Docker Compose** - Database containerization
- **pgAdmin 4** - Database administration
- **Git** - Version control
- **Maven** - Build automation

---

## 📁 Project Structure

```
decentkids/
├── backend/                    # Spring Boot Application
│   ├── src/main/java/com/decentkids/
│   │   ├── config/            # Configuration classes
│   │   │   ├── SecurityConfig.java      # Security & JWT config
│   │   │   ├── OpenApiConfig.java       # Swagger configuration
│   │   │   └── DataSeeder.java          # Initial data seeding
│   │   ├── controller/        # REST API Controllers
│   │   │   ├── AuthController.java      # Authentication endpoints
│   │   │   ├── ProductController.java   # Product management
│   │   │   ├── OrderController.java     # Order management
│   │   │   └── AdminController.java     # Admin operations
│   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── ApiResponse.java         # Standard API response
│   │   │   ├── AuthDto.java             # Authentication DTOs
│   │   │   └── ProductOrderDto.java     # Product & Order DTOs
│   │   ├── entity/           # JPA Entities
│   │   │   ├── Customer.java            # Customer entity
│   │   │   ├── Admin.java               # Admin entity
│   │   │   ├── Product.java             # Product entity
│   │   │   ├── Order.java               # Order entity
│   │   │   └── OrderItem.java           # Order items
│   │   ├── repository/       # Data Access Layer
│   │   │   ├── CustomerRepository.java
│   │   │   ├── AdminRepository.java
│   │   │   ├── ProductRepository.java
│   │   │   └── OrderRepository.java
│   │   ├── service/          # Business Logic Layer
│   │   │   ├── AuthService.java         # Authentication logic
│   │   │   ├── ProductService.java      # Product operations
│   │   │   └── OrderService.java        # Order processing
│   │   ├── security/         # Security Components
│   │   │   ├── JwtTokenProvider.java    # JWT utility
│   │   │   └── JwtAuthenticationFilter.java
│   │   └── exception/        # Exception Handling
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ResourceNotFoundException.java
│   │       ├── BadRequestException.java
│   │       └── UnauthorizedException.java
│   ├── src/main/resources/
│   │   └── application.properties      # App configuration
│   └── pom.xml                        # Maven dependencies
├── frontend/                  # Frontend Application
│   ├── index.html            # Login & Registration page
│   ├── customer.html         # Customer homepage
│   ├── products.html         # Product catalog
│   ├── cart.html            # Shopping cart
│   ├── admin.html           # Admin dashboard
│   └── profile.html         # User profile
├── docker-compose.yml        # Database setup
├── .gitignore
└── README.md                 # This file
```

---

## ⚙️ Configuration

### Database Configuration

The application uses PostgreSQL running in Docker. Configuration in `application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/decentkids_db
spring.datasource.username=postgres
spring.datasource.password=decentkids@123

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# JWT Configuration
jwt.secret=decentkidsSecretKey2024ForDevelopmentOnly
jwt.expiration=86400000
```

### Docker Services

```yaml
# docker-compose.yml
services:
  postgres:
    image: postgres:15
    container_name: decentkids_postgres
    environment:
      POSTGRES_DB: decentkids_db
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: decentkids@123
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: decentkids_pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@decentkids.com
      PGADMIN_DEFAULT_PASSWORD: admin123
    ports:
      - "5050:80"
    depends_on:
      - postgres

volumes:
  postgres_data:
```

### Environment Variables

For production deployment:

```bash
# Database
DB_HOST=your-db-host
DB_PORT=5432
DB_NAME=decentkids_db
DB_USERNAME=postgres
DB_PASSWORD=your-secure-password

# JWT
JWT_SECRET=your-256-bit-secret-key
JWT_EXPIRATION=86400000

# Server
SERVER_PORT=8080
```

---

## 🔌 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Authentication Endpoints

#### Customer Registration
```http
POST /auth/customer/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "password123",
  "phone": "9876543210",
  "age": 30,
  "gender": "Male",
  "address": "123 Main St, City"
}
```

#### Customer Login
```http
POST /auth/customer/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "password123"
}
```

#### Admin Login
```http
POST /auth/admin/login
Content-Type: application/json

{
  "email": "admin@decentkids.com",
  "password": "admin123"
}
```

### Product Endpoints

#### Get All Products (with pagination)
```http
GET /products?page=0&size=12
```

#### Search Products
```http
GET /products/search?query=shirt&page=0&size=12
```

#### Get Products by Category
```http
GET /products/category/TOPS?page=0&size=12
```

### Order Endpoints

#### Place Order (Customer)
```http
POST /orders
Authorization: Bearer your-jwt-token
Content-Type: application/json

{
  "customerId": 1,
  "orderItems": [
    {
      "productId": 1,
      "quantity": 2,
      "price": 599.00
    }
  ],
  "total": 1198.00,
  "deliveryCharge": 0,
  "discount": 0,
  "notes": "Please handle with care"
}
```

#### Get Customer Orders
```http
GET /orders/customer/{customerId}
Authorization: Bearer your-jwt-token
```

### Admin Endpoints (Protected)

#### Get Dashboard Data
```http
GET /admin/dashboard
Authorization: Bearer your-admin-jwt-token
```

#### Add Product
```http
POST /admin/products
Authorization: Bearer your-admin-jwt-token
Content-Type: application/json

{
  "name": "Cotton T-Shirt",
  "description": "Comfortable cotton t-shirt for kids",
  "price": 599.00,
  "stock": 100,
  "category": "TOPS",
  "imageUrl": "https://example.com/image.jpg",
  "ageGroup": "6-8 Years",
  "sizes": "S, M, L"
}
```

#### Get All Customers
```http
GET /admin/customers
Authorization: Bearer your-admin-jwt-token
```

### Standard Response Format

All API endpoints return responses in this format:

```json
{
  "success": true,
  "message": "Operation completed successfully",
  "data": {
    // Response data here
  },
  "timestamp": "2024-01-01T12:00:00.000Z"
}
```

### Error Response Format

```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2024-01-01T12:00:00.000Z"
}
```

---

## 🧪 Testing

### Manual Testing Guide

#### 1. Database Setup Test
```bash
# Start database
docker compose up -d

# Verify PostgreSQL is running
docker compose ps

# Check logs if needed
docker compose logs postgres
```

#### 2. Application Startup Test
```bash
cd backend
mvn clean install
mvn spring-boot:run

# Verify startup in logs:
# - "Started DecentKidsApplication"
# - "Tomcat started on port 8080"
```

#### 3. API Health Check
```bash
# Test basic endpoint
curl http://localhost:8080/api/products

# Should return products list or empty array
```

#### 4. Authentication Flow Test

**Customer Registration:**
```bash
curl -X POST http://localhost:8080/api/auth/customer/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Customer",
    "email": "test@example.com", 
    "password": "password123",
    "phone": "9876543210",
    "age": 25,
    "gender": "Male",
    "address": "Test Address"
  }'
```

**Customer Login:**
```bash
curl -X POST http://localhost:8080/api/auth/customer/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'
```

**Admin Login:**
```bash
curl -X POST http://localhost:8080/api/auth/admin/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "admin@decentkids.com",
    "password": "admin123"
  }'
```

#### 5. Frontend Integration Test

1. **Open Frontend**: Navigate to `http://localhost:8080`
2. **Test Customer Flow**:
   - Register a new customer
   - Login with credentials
   - Browse products
   - Add items to cart
   - Place an order
3. **Test Admin Flow**:
   - Login as admin
   - View dashboard
   - Add a new product
   - View customers and orders

#### 6. Database Verification

Access pgAdmin at `http://localhost:5050`:
- Email: `admin@decentkids.com`
- Password: `admin123`

Verify tables:
- `customers`
- `admins`
- `products`
- `orders`
- `order_items`

### Common Issues & Solutions

#### Issue: Application won't start
```bash
# Check if port 8080 is in use
lsof -i :8080

# Kill process if needed
kill -9 $(lsof -t -i :8080)
```

#### Issue: Database connection failed
```bash
# Check if PostgreSQL is running
docker compose ps

# Restart database
docker compose down
docker compose up -d
```

#### Issue: JWT token errors
- Check `application.properties` for correct JWT secret
- Verify token expiration time
- Clear browser localStorage and re-login

#### Issue: CORS errors
- Spring Security is configured to allow all origins for development
- Check `SecurityConfig.java` if issues persist

---

## 🚢 Deployment

### Development Deployment

1. **Using Spring Boot Dev Tools**:
```bash
cd backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

2. **Using Maven**:
```bash
mvn clean package
java -jar target/decentkids-0.0.1-SNAPSHOT.jar
```

### Production Deployment

#### Option 1: Traditional Server Deployment

1. **Build the application**:
```bash
mvn clean package -DskipTests
```

2. **Set environment variables**:
```bash
export DB_HOST=your-production-db-host
export DB_PASSWORD=your-secure-password
export JWT_SECRET=your-256-bit-production-secret
```

3. **Deploy**:
```bash
java -jar target/decentkids-0.0.1-SNAPSHOT.jar \
  --server.port=8080 \
  --spring.profiles.active=prod
```

#### Option 2: Docker Deployment

1. **Create Dockerfile**:
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/decentkids-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

2. **Build and run**:
```bash
docker build -t decentkids-app .
docker run -p 8080:8080 \
  -e DB_HOST=your-db-host \
  -e DB_PASSWORD=your-password \
  decentkids-app
```

#### Option 3: Cloud Deployment (Heroku)

1. **Add system.properties**:
```properties
java.runtime.version=17
```

2. **Configure for cloud database**:
```properties
# In application-prod.properties
spring.datasource.url=${DATABASE_URL}
spring.jpa.hibernate.ddl-auto=update
```

3. **Deploy**:
```bash
git add .
git commit -m "Deploy to production"
git push heroku main
```

### Production Environment Variables

```bash
# Database Configuration
DB_HOST=production-db-host
DB_PORT=5432
DB_NAME=decentkids_prod
DB_USERNAME=postgres
DB_PASSWORD=secure-production-password

# JWT Configuration  
JWT_SECRET=your-very-secure-256-bit-secret-key-here
JWT_EXPIRATION=86400000

# Server Configuration
SERVER_PORT=8080
SPRING_PROFILES_ACTIVE=prod

# Optional: SSL Configuration
SERVER_SSL_ENABLED=true
SERVER_SSL_KEY_STORE=path/to/keystore.p12
SERVER_SSL_KEY_STORE_PASSWORD=keystore-password
```

### Performance Optimization

1. **JVM Tuning**:
```bash
java -Xms512m -Xmx1024m -jar app.jar
```

2. **Database Connection Pool**:
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```

3. **Caching** (Future enhancement):
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-cache</artifactId>
</dependency>
```

---

## 👥 Contributing

### Development Setup

1. **Fork the repository**
2. **Clone your fork**:
```bash
git clone https://github.com/your-username/decentkids.git
```
3. **Create a feature branch**:
```bash
git checkout -b feature/your-feature-name
```
4. **Make changes and test**
5. **Commit with clear message**:
```bash
git commit -m "feat: add product filtering by age group"
```
6. **Push and create Pull Request**

### Coding Standards

#### Backend (Java)
- Use **Lombok** for boilerplate reduction
- Follow **Spring Boot** best practices
- All endpoints return `ApiResponse<T>`
- Use `@Valid` for request validation
- Constructor injection over field injection
- `BigDecimal` for monetary values
- `@Transactional` for write operations

#### Frontend (JavaScript)
- Use **ES6+** features
- Consistent naming: `decentkids_*` for localStorage
- Fetch API for HTTP calls
- Proper error handling
- Mobile-first responsive design

#### Database
- Use **camelCase** in Java entities
- Use **snake_case** in database columns
- Foreign key relationships properly mapped
- Proper indexing on search fields

### Commit Message Convention

```
feat: add new feature
fix: bug fix
docs: documentation changes
style: formatting changes
refactor: code refactoring
test: add tests
chore: maintenance tasks
```

### Pull Request Guidelines

1. **Clear title and description**
2. **Reference related issues**
3. **Include screenshots for UI changes**
4. **Ensure all tests pass**
5. **Update documentation if needed**

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 📞 Support & Contact

- **Project Lead**: Kishor
- **GitHub Issues**: [Report bugs or request features](https://github.com/kishor3107/decentkids/issues)
- **Email**: kishor3107@example.com

---

## 🏆 Acknowledgments

- **Spring Boot Team** - Excellent framework for Java development
- **PostgreSQL Community** - Robust database system  
- **Bootstrap Team** - Beautiful and responsive UI framework
- **Docker Team** - Simplified development environment

---

## 🔮 Roadmap

### Upcoming Features

- [ ] **Payment Integration** - Razorpay/Stripe integration
- [ ] **Email Notifications** - Order confirmations and updates
- [ ] **Product Reviews** - Customer feedback system
- [ ] **Wishlist** - Save products for later
- [ ] **Inventory Alerts** - Low stock notifications
- [ ] **Advanced Search** - Filters by size, age, price range
- [ ] **Order Tracking** - Real-time delivery status
- [ ] **Discount Coupons** - Promotional code system
- [ ] **Mobile App** - React Native mobile application
- [ ] **Analytics Dashboard** - Sales and customer insights

### Technical Improvements

- [ ] **Redis Caching** - Improve performance
- [ ] **Unit Tests** - Comprehensive test coverage
- [ ] **API Rate Limiting** - Prevent abuse
- [ ] **File Upload** - Product image management
- [ ] **Microservices** - Split into smaller services
- [ ] **CI/CD Pipeline** - Automated deployment
- [ ] **Monitoring** - Application health monitoring
- [ ] **API Versioning** - Future-proof API design

---

**Made with ❤️ for kids' fashion**