# 🧑‍💼 Kafoor Backend — User Management Service

A microservice responsible for user account management in the **Kafoor** online quiz platform. Handles registration, authentication, profile management, and role-based access control.

## 📌 Key Features

- 🔐 Secure authentication using JWT (Access + Refresh tokens)  
- 👤 Profile management (update name, email, nickname)  
- 🛡️ Role-based access control (RBAC)  
- 🔄 Automated database migrations with Flyway  
- 📄 API documentation via Swagger UI (OpenAPI 3)  
- 🧪 Input validation using Bean Validation (JSR-380)  


## 🛠 Tech Stack

- **Language**: Java 23
- **Framework**: Spring Boot 3.x  
- **Database**: MySQL 8.0  
- **ORM**: Spring Data JPA + Hibernate  
- **Security**: Spring Security + JWT (via `jjwt`)  
- **Migrations**: Flyway  
- **Build Tool**: Gradle  
- **API Docs**: Springdoc OpenAPI (Swagger UI)  
- **DTO Mapping**: MapStruct + Lombok  


## 🚀 Quick Start

### Prerequisites
- Docker and Docker Compose *(recommended)*  
- OR locally: JDK 23+, MySQL 8.0

### Run with Docker Compose (Recommended)

1. Clone the repository:
```bash
git clone https://github.com/KirillMikhailov442/kafoor-backend-users.git
cd kafoor-backend-users
```

2. Start the services:
```bash
docker-compose up --build
```
3. The service will be available at:
🔗 http://localhost:8081

4. Explore the API documentation:
📘 http://localhost:8081/swagger-ui.html

## 🔐 Security

Protected endpoints require the Authorization header:
> Authorization: Bearer <access_token>
* Tokens are issued on login and can be refreshed via /auth/update-tokens.
* Passwords are securely hashed using BCrypt.

## 📖 API Documentation

Open in browser: http://localhost:8081/swagger-ui.html

## 📄 License

This project is licensed under the MIT License.
See LICENSE for details.