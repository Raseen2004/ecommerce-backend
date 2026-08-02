# E-Commerce Learning API

A production-style e-commerce backend built with Spring Boot, Spring Security, JWT authentication, and PostgreSQL. This project models the core workflows of an online shopping system, including customer registration, catalog management, cart operations, order placement, address management, and payment processing.

It was developed as a hands-on backend learning project with a strong focus on layered architecture, RESTful API design, validation, persistence, and secure authentication.

## Project Overview

This application exposes a secure REST API for managing common e-commerce operations:

- Customer registration and login with JWT-based authentication
- Product and category management
- Customer profile and address management
- Shopping cart creation and item updates
- Order placement from cart items
- Payment processing and order confirmation

The codebase is organized using a clean layered structure:

- `controller` for HTTP endpoints
- `service` for business logic
- `repository` for data access
- `entity` for persistence models
- `dto` for request and response contracts
- `mapper` for entity-to-DTO transformation
- `security` for JWT authentication and request filtering

## Tech Stack

- Java 25
- Spring Boot 4.1
- Spring Web MVC
- Spring Data JPA
- Spring Security
- JWT (`jjwt`)
- PostgreSQL
- Lombok
- Maven

## Key Features

### Authentication and Security

- User registration and login endpoints
- Password hashing with `BCryptPasswordEncoder`
- Stateless authentication using JWT
- Custom JWT request filter integrated with Spring Security
- Protected API routes with public access limited to authentication endpoints

### Catalog Management

- Create, update, delete, and fetch products
- Create, update, delete, and fetch categories
- Filter products by category
- Inventory tracking through product stock management

### Customer and Address Management

- Create and manage customer records
- Add, update, list, and delete customer addresses
- Link addresses directly to customers for order fulfillment

### Cart and Checkout Flow

- Automatically creates a cart during customer registration
- Add items to cart
- Update cart item quantities
- Remove items or clear the cart
- Place orders from the current cart

### Orders and Payments

- Converts cart items into order items during checkout
- Calculates total order amount
- Validates stock availability before placing an order
- Creates a payment record for each order
- Processes payment and updates order status to confirmed

## Business Flow

The typical flow supported by the API is:

1. Register a customer and receive a JWT token.
2. Authenticate future requests with `Authorization: Bearer <token>`.
3. Create categories and products.
4. Add products to a customer's cart.
5. Save one or more customer addresses.
6. Place an order using the cart and selected address.
7. Process the generated payment to confirm the order.

## API Modules

### Public Endpoints

- `POST /api/auth/register`
- `POST /api/auth/login`

### Protected Endpoints

- `Customers` - `/api/customers`
- `Addresses` - `/api/customers/{customerId}/addresses`, `/api/addresses/{id}`
- `Categories` - `/api/categories`
- `Products` - `/api/products`
- `Cart` - `/api/customers/{customerId}/cart`
- `Orders` - `/api/customers/{customerId}/orders`, `/api/orders/{id}`
- `Payments` - `/api/payments/{id}`, `/api/orders/{orderId}/payment`

## Data Model Highlights

The domain includes the following core entities:

- `Customer`
- `Address`
- `Category`
- `Product`
- `Cart`
- `CartItem`
- `Order`
- `OrderItem`
- `Payment`

Relationships are modeled to reflect a realistic e-commerce workflow, including:

- One customer to one cart
- One customer to many addresses
- One order to many order items
- One order to one payment

## Validation and Error Handling

- Request payloads use Jakarta Validation annotations
- A global exception handler centralizes API error responses
- Custom `ResourceNotFoundException` is used for missing records
- Business rules such as duplicate email registration, empty-cart checkout, and insufficient stock are enforced at the service layer

## Getting Started

### Prerequisites

- Java 25
- Maven
- PostgreSQL

### Configuration

Create your runtime configuration from the example file:

```bash
src/main/resources/application.example.yaml
```

Set the following values for your local PostgreSQL instance:

- database URL
- database username
- database password

You should also add your JWT secret in your application configuration before running the project.

### Run the Application

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

The application runs by default on:

```text
http://localhost:8080
```

## Example Auth Header

Use the JWT token returned from login or registration in protected requests:

```http
Authorization: Bearer <your-jwt-token>
```

## Why This Project Stands Out

This project demonstrates practical backend engineering skills that are directly relevant to real-world product development:

- Designing a layered Spring Boot application
- Building secure REST APIs with JWT authentication
- Modeling relational data with JPA
- Implementing business rules for carts, orders, and payments
- Structuring maintainable code with DTOs, mappers, services, and repositories

## Future Improvements

Potential next steps for expanding the project:

- Add role-based authorization for admin-only operations
- Introduce Swagger/OpenAPI configuration
- Add unit and integration tests for services and controllers
- Implement pagination, sorting, and product search
- Add payment gateway integration
- Containerize with Docker and deploy to the cloud

## Author

Developed by Raseen as a backend-focused learning project to strengthen Spring Boot, security, and database design skills through an end-to-end e-commerce domain.
