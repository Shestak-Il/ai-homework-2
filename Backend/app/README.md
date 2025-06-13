# Backend API (Spring Boot, PostgreSQL, JWT)

This project is a backend API that replicates the behavior and structure of [JSONPlaceholder](https://jsonplaceholder.typicode.com), with full REST operations, JWT-based authentication, structured user data storage, and containerized deployment.

## Features
- Full CRUD for users (mimics JSONPlaceholder /users)
- JWT authentication (register/login)
- PostgreSQL database
- Spring Boot (Java 21, Gradle)
- Docker & Docker Compose
- Seeded with example user data
- Unit and integration tests
- DTO-based API with MapStruct mapping

## Setup

### Prerequisites
- Docker & Docker Compose
- Java 21 (for local dev)
- Gradle (for local dev)

### Running with Docker Compose
```
docker-compose up --build
```
The API will be available at `http://localhost:8080`.

### Running Locally
1. Configure PostgreSQL in `application.properties`.
2. Run: `./gradlew bootRun` (from the `app` directory)

## API Endpoints

### Auth

#### Register User
- **Endpoint**: `POST /auth/register`
- **Description**: Register a new user
- **Request Body**:
  ```json
  {
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response**: `200 OK`
  ```json
  "User registered"
  ```
- **Error Response**: `400 Bad Request`
  ```json
  "Email already in use"
  ```

#### Login
- **Endpoint**: `POST /auth/login`
- **Description**: Login and receive JWT token
- **Request Body**:
  ```json
  {
    "email": "john@example.com",
    "password": "password123"
  }
  ```
- **Response**: `200 OK`
  ```json
  {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
  ```
- **Error Response**: `401 Unauthorized`
  ```json
  "Invalid credentials"
  ```

### Users (JWT required)

#### List All Users
- **Endpoint**: `GET /users`
- **Description**: Get all users
- **Headers**: `Authorization: Bearer <token>`
- **Response**: `200 OK`
  ```json
  [
    {
      "id": 1,
      "name": "Leanne Graham",
      "username": "Bret",
      "email": "Sincere@april.biz",
      "address": {
        "street": "Kulas Light",
        "suite": "Apt. 556",
        "city": "Gwenborough",
        "zipcode": "92998-3874",
        "geo": {
          "lat": "-37.3159",
          "lng": "81.1496"
        }
      },
      "phone": "1-770-736-8031 x56442",
      "website": "hildegard.org",
      "company": {
        "name": "Romaguera-Crona",
        "catchPhrase": "Multi-layered client-server neural-net",
        "bs": "harness real-time e-markets"
      }
    }
  ]
  ```

#### Get User by ID
- **Endpoint**: `GET /users/{id}`
- **Description**: Get a specific user by ID
- **Headers**: `Authorization: Bearer <token>`
- **Response**: `200 OK`
  ```json
  {
    "id": 1,
    "name": "Leanne Graham",
    "username": "Bret",
    "email": "Sincere@april.biz",
    "address": {
      "street": "Kulas Light",
      "suite": "Apt. 556",
      "city": "Gwenborough",
      "zipcode": "92998-3874",
      "geo": {
        "lat": "-37.3159",
        "lng": "81.1496"
      }
    },
    "phone": "1-770-736-8031 x56442",
    "website": "hildegard.org",
    "company": {
      "name": "Romaguera-Crona",
      "catchPhrase": "Multi-layered client-server neural-net",
      "bs": "harness real-time e-markets"
    }
  }
  ```
- **Error Response**: `404 Not Found` (if user doesn't exist)

#### Create User
- **Endpoint**: `POST /users`
- **Description**: Create a new user (ID will be automatically generated)
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  {
    "name": "Jane Doe",
    "username": "janedoe",
    "email": "jane@example.com",
    "address": {
      "street": "123 Main St",
      "suite": "Apt 1",
      "city": "New York",
      "zipcode": "10001",
      "geo": {
        "lat": "40.7128",
        "lng": "-74.0060"
      }
    },
    "phone": "123-456-7890",
    "website": "jane.com",
    "company": {
      "name": "Jane Corp",
      "catchPhrase": "Catchy phrase",
      "bs": "BS"
    }
  }
  ```
- **Response**: `200 OK` (with created user including generated ID)
- **Error Response**: `400 Bad Request` (if required fields are missing)

#### Update User
- **Endpoint**: `PUT /users/{id}`
- **Description**: Update an existing user
- **Headers**: `Authorization: Bearer <token>`
- **Request Body**:
  ```json
  {
    "name": "Jane Smith",
    "username": "janesmith",
    "email": "jane.smith@example.com",
    "address": {
      "street": "456 Oak St",
      "suite": "Suite 2",
      "city": "Boston",
      "zipcode": "02108",
      "geo": {
        "lat": "42.3601",
        "lng": "-71.0589"
      }
    },
    "phone": "987-654-3210",
    "website": "janesmith.com",
    "company": {
      "name": "Smith Corp",
      "catchPhrase": "New phrase",
      "bs": "New BS"
    }
  }
  ```
- **Response**: `200 OK` (with updated user)
- **Error Response**: `404 Not Found` (if user doesn't exist)

#### Delete User
- **Endpoint**: `DELETE /users/{id}`
- **Description**: Delete a user
- **Headers**: `Authorization: Bearer <token>`
- **Response**: `204 No Content` (on successful deletion)
- **Error Response**: `404 Not Found` (if user doesn't exist)

## Data Transfer Objects (DTOs)

The API uses DTOs to separate the internal entity model from the API contract. This provides better control over the data exposed through the API and ensures type safety.

### DTO Structure
- `UserRequest`/`UserResponse`: Main user data
- `AddressRequest`/`AddressResponse`: Address information
- `GeoRequest`/`GeoResponse`: Geographic coordinates
- `CompanyRequest`/`CompanyResponse`: Company information

### Mapping
The application uses MapStruct for efficient mapping between DTOs and entities:
- `UserMapper`: Maps between User entity and DTOs
- `AddressMapper`: Maps between Address entity and DTOs
- `CompanyMapper`: Maps between Company entity and DTOs

## Authentication
- Register and login to receive a JWT
- Pass the JWT in the `Authorization: Bearer <token>` header for protected endpoints
- JWT tokens expire after 24 hours

## Configuration
- The application uses Spring Boot's auto-configuration for PostgreSQL and JPA
- Database settings are in `application.properties`
- JWT secret key is hardcoded in `SecurityConfig.java` (replace with a secure key in production)

## Testing
- Run tests with: `./gradlew test`
- Tests are located in `src/test/java/com/example/backend`

## Project Structure
- `User`, `Address`, `Geo`, `Company` — Entity models
- `UserController` — REST endpoints
- `AuthController` — Auth endpoints
- `SecurityConfig` — JWT security
- `dto` package — Data Transfer Objects
- `mapper` package — MapStruct mappers
- `data.sql` — Seed data
