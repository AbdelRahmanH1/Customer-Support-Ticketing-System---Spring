# API Documentation
# 🛠️ Customer Support Ticketing System

## 📚 Table of Contents
- [Overview](#overview)
- [Technologies Used](#-technologies-used)
- [Redis Features](#-redis-features)
- [Environment Setup](#-environment-setup)
- [Running the Application](#-running-the-application)
- [Authentication](#authentication)
- [User](#user)
- [Ticket](#ticket)
- [Reply](#reply)
- [Audit Logs](#audit_logs)
- [Models](#models)
- [API Documentation (Swagger)](#-api-documentation-swagger)
- [Authentication & Authorization](#-authentication--authorization)
- [Contributing](#-contributing)
- [Contact](#-contact)

## Overview

A backend system for managing support tickets, user roles, and replies. Built with Spring Boot and designed for real-world usage with clean architecture, authentication, and audit logging.

## 🧰 Technologies Used

- ☕ Java 21
- ⚙️ Spring Boot 3.x
- 🔐 Spring Security (JWT)
- 🐬 MySQL + JPA (Hibernate)
- 🧠 MapStruct
- ✅ Jakarta Validation
- 🦾 Lombok
- 🧠 Redis

---

## 📦 Redis Features

✅ Redis is actively used to handle:
- 🔑 **Reset Password Tokens** – temporary codes with expiration
- 🔁 **Refresh Tokens** – track per-device tokens with `userId`, `isValid`, TTL

---

## ⚙️ Environment Setup

Ensure you have the following tools installed:

- Java 21
- MySQL Server
- Redis (optional, future enhancement)

Clone the repository and install dependencies:

```bash
git clone https://github.com/AbdelRahmanH1/Customer-Support-Ticketing-System---Spring.git
cd Customer-Support-Ticketing-System---Spring
```

Create a .env file based on .env.example:

```bash
JWT_SECRET=your_secret
DB_URL=jdbc:mysql://localhost:3306/tickets?createDatabaseIfNotExist=true
DB_USERNAME=root
DB_PASSWORD=yourpassword
MAIL_HOST=host
MAIL_PORT=port
MAIL_USERNAME=username
MAIL_PASSWORD=password
```

## 🚀 Running the Application

Run the project 

```bash
./mvnw spring-boot:run
```

## Authentication

### Login

- **Endpoint:** `POST auth/signin`
- **Request Body:**
  ```json
  {
    "email": "string",
    "password": "string"
  }
  ```
  **Response result:**
  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": {
      "access_token": "string"
    } 
  }
  ```

### Signup

- **Endpoint:** `POST auth/register`
- **Request Body:**

  ```json
  {
    "name": "string",
    "email": "string",
    "password": "string"
  }
  ```

  **Response result:**

  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": null
  }
  ```

### Refresh Token

- **Endpoint:** `POST auth/refresh`
- **Request Body:**
   No request body required.
  

  **Response result:**

  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": {
    "access_token": "string"
    }
 }
  ```

### Forget-Password

- **Endpoint:** `POST auth/forget-password`
- **Request Body:**

  ```json
  {
    "email": "string"
  }
  ```

  **Response result:**

  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": null
  }
  ```

### Reset Password

- **Endpoint:** `POST auth/reset-password`
- **Request Body:**

  ```json
  {
    "email": "string",
    "newPassword": "string",
    "code": "string"
  }
  ```

  **Response result:**

  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": null
  }
  ```
  
## User 

### getCurrent User

- **Endpoint:** `GET /users`
- **Authorization:** `Bearer [token]`
- **Response Schema:**
  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": {
      "id": "long",
      "name": "string",
      "email" : "string"
   }
  }
  ```

### Change Password
- **Endpoint:** `PUT /users`
- **Authorization:** `Bearer [token]`
- **Request Body:**

  ```json
  {
    "password": "string"
  }
  ```

  **Response Schema:**

  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": null
  }
  ```

### Delete Account

- **Endpoint:** `DELETE /users`
- **Authorization:** `Bearer [token]`
- **Request Body:**
    no request body required

  **Response Schema:**
  ```json
  {
    "success":"boolean",
    "message": "string",
    "data": null
  }
  ```
---
## Ticket
### create Ticket

- **Endpoint:** `POST /tickets`
- **Authorization:** `Bearer [token]`
- **Request Body:**
  ```json
  {
    "ticket": "string",
    "description": "string"
  }
  ```
  **Response Schema:**
  ```json
  {
    "success": "boolean",
    "message": "string",
    "data": {
      "id": "Long",
      "title": "string",
      "description": "string",
      "userId": "long",
      "status": "string",
      "createdDate": "date"  
    }  
  }
  ```

### Get tickets

- **Endpoint:** `GET /tickets`
- **Authorization:** `Bearer [token]`
- **Description:** 
  - Returns a paginated list of tickets.
      - Regular users receive their own tickets only.
      - Admins can view all or filter by user ID.
- **Query Params:**
    - `page` (optional, default: 0)
    - `userId` (admin only) – filter tickets by a specific user ID

  **Response Schema:**
  ```json
  {
  "success": true,
  "message": "Tickets fetched successfully",
  "data": {
    "content": [
      {
        "id": 12,
        "title": "Bug in login",
        "description": "Unable to login using Google.",
        "status": "OPEN",
        "createdAt": "2025-06-20T11:00:00",
        "updatedAt": "2025-06-20T12:00:00"
      }
    ],
    "totalPages": 5,
    "totalElements": 42,
    "pageNumber": 0
   }
  }

  ```

### Get a ticket by ID

- **Endpoint:** `GET /tickets/{ticket_id}`
- **Authorization:** `Bearer [token]`
- **Request Body:** no required
  **Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": {
    "id": "Long",
    "title": "string",
    "description": "string",
    "userId": "long",
    "status": "string",
    "createdDate": "date"  
    }  
  }
  ```
### Update a ticket by ID

- **Endpoint:** `PUT /tickets/{ticket_id}`
- **Authorization:** `Bearer [token]`
- **Request Body:**

```json
    {
      "ticket": "string",
      "description": "string"
    } 
```
  **Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": {
    "id": "Long",
    "title": "string",
    "description": "string",
    "userId": "long",
    "status": "string",
    "createdDate": "date"  
    }  
  }
  ```
### Update status for a ticket by ID

- **Endpoint:** `PATCH /tickets/{ticket_id}`
- **Authorization:** `Bearer [token]`
- **Role Required:** `ADMIN`
- **Request Body:**

```json
    {
      "status": "string"
    } 
```
**Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": {
    "id": "Long",
    "title": "string",
    "description": "string",
    "userId": "long",
    "status": "string",
    "createdDate": "date"  
    }  
  }
  ```
---

## Reply
### Create Reply
- **Endpoint:** `POST /tickets/{ticket_id}/replies`
- **Authorization:** `Bearer [token]`
- **Request Body:**

```json
    {
      "message": "string"
    } 
```
**Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": {
    "id": "Long",
    "SenderRole": "string",
    "message": "string",
    "createdAt": "Date"
    }  
  }
  ```
### Get Replies
- **Endpoint:** `GET /tickets/{ticket_id}/replies`
- **Authorization:** `Bearer [token]`
- **Request Body:** no request body required

**Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": [
    {
      "id": "Long",
      "SenderRole": "string",
      "message": "string",
      "createdAt": "Date"
    }
   ]
  }
  ```
### delete replies (ADMIN)
- **Endpoint:** `DELETE /tickets/{ticket_id}/replies`
- **Authorization:** `Bearer [token]`
- **Request Body:** no request body required

**Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": null
}
  ```

### Audit logs
### Get All logs (ADMIN only)
- **Endpoint:** `GET /audit-logs`
- **Authorization:** `Bearer [token]`
- **Request Body:** no request body required

**Response Schema:**
  ```json
  {
  "success": "boolean",
  "message": "string",
  "data": null
}
  ```
---

## Models

### 👤 User Model
- `id`:Long
- `name`:String
- `email`:String
- `password`:String
- `role`:ENUM (USER,ADMIN)
- `deleted`:boolean
- `created_at`:DateTime
- `updated_at`:DateTime

### 🎫 ticket
- `id`:Long
- `title`:String
- `description`:String
- `status`: ENUM(OPEN,IN_PROGRESS,CLOSED)
- `user_id`:Long (Owner)
- `admin_responder_id`:Long (Optional)
- `created_at`:DateTime
- `updated_at`:DateTime
- `end_time`:DateTime

### 💬 reply
- `id`:Long
- `ticket_id`:Long (Ticket)
- `user_id`:Long (User)
- `message`:String
- `created_at`:DateTime

### 📜 Audit_log
- `id`:Long
- `action`:String
- `user_id`:Long (User)
- `metadata`:String
- `created_at`:DateTime
---

## 📖 API Documentation (Swagger)
  This project includes **live API documentation** via **Swagger UI**.

###  🔗 How to Access

Once the application is running, visit:
 ```
  http://localhost:8080/swagger-ui/index.html
 ```

### 📌Features

- ✅ 🔎 Browse all available API endpoints
- ✅ 📄 View request and response schemas
- ✅ 🧪 Test endpoints directly in your browser (JWT auth supported)
- ✅ 🔐 See role-based access for each endpoint

### 🛠️ Powered by
- `springdoc-openapi` for Spring Boot 3
---

## 🔐 Authentication & Authorization
- Use **JWT (JSON WEB TOKEN)** for authentication
- Tokens are parsed in the `Authorization` header as:

```http
Authorization: Bearer <access_token>
```

### Roles
- **USER:** can create/ update their tickets, send replies
- **ADMIN:** can update ticket status, reply to any ticket, delete replies, view audit logs

---

## 🤝 Contributing
 This project is open for suggestions and improvements. If you'd like to contribute, ``feel free to open issues or submit pull requests.``

---

## 📬 Contact
 `Feel free to check out the project, use it, and share feedback!`
- 💻 GitHub: [Abdelrahman Hossam](https://github.com/AbdelRahmanH1)
- 💼 LinkedIn: [Abdelrahman Hossam](https://www.linkedin.com/in/abdelrahmanh1/)

---
## 📄 License
This project is licensed under the MIT License.

MIT License

Copyright (c) 2025 Abdelrahman Hossam

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights  
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell  
copies of the Software, and to permit persons to whom the Software is  
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all  
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,  
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE  
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER  
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,  
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  
SOFTWARE.
