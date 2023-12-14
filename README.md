# Spring Authentication

This project is a Spring Boot application that provides authentication and authorization functionalities using JSON Web Tokens (JWT).

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Configuration](#configuration)
  - [JWT Configuration](#jwt-configuration)
- [Usage](#usage)
  - [Authentication](#authentication)
  - [Authorization](#authorization)
- [Endpoints](#endpoints)
- [Security](#security)
- [Contributing](#contributing)
- [License](#license)

## Introduction

This project demonstrates the implementation of a secure authentication system in a Spring Boot application. It uses JWTs to ensure secure communication and authorization of users.

## Features

- User authentication with JWTs
- Token-based authorization
- Registration of new users
- Token refresh mechanism

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven
- MySQL (optional, for data storage)

### Installation

1. Clone the repository:

   \`\`\`bash
   git clone https://github.com/bikirahman/spring-authentication.git
   cd spring-authentication
   \`\`\`

2. Build the project:

   \`\`\`bash
   mvn clean install
   \`\`\`

3. Run the application:

   \`\`\`bash
   java -jar target/authentication-0.0.1-SNAPSHOT.jar
   \`\`\`

The application will start on \`http://localhost:8080\`.

## Configuration

### JWT Configuration

The JWT configuration is located in the \`application.properties\` file. Customize the following properties as needed:

\`\`\`properties
# JWT Configuration
jwt.secret=your-secret-key
jwt.token-validity=30
\`\`\`

- \`jwt.secret\`: Secret key for JWT token generation.
- \`jwt.token-validity\`: Token validity period in minutes.

## Usage

### Authentication

To authenticate a user, send a POST request to \`/auth/login\` with the user credentials:

\`\`\`bash
curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"your-username", "password":"your-password"}'
\`\`\`

The response will contain a JWT token and a refresh token.

### Authorization

Include the JWT token in the Authorization header for authorized requests:

\`\`\`bash
curl -X GET http://localhost:8080/api/protected-resource -H "Authorization: Bearer your-jwt-token"
\`\`\`

## Endpoints

- \`/auth/login\`: User authentication endpoint.
- \`/auth/register\`: Register a new user.
- \`/auth/refresh-token\`: Refresh the JWT token using a refresh token.
- \`/api/protected-resource\`: Example protected resource.

For detailed API documentation, refer to the Swagger UI at \`http://localhost:8080/swagger-ui/\`.

## Security

This application uses Spring Security for authentication and authorization. The JWT token ensures secure communication between the client and the server.

## License

This project is licensed under the MIT License
