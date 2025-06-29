# API Documentation
# 🛠️ Customer Support Ticketing System

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

### refresh-token

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

### reset-Password

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
- **Request Body:**
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

### change password
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

### Delete User

- **Endpoint:** `GET /users`
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
- **Description:** Return pagination list of tickets User will receive their own tickets
- **Query Params:**
    - page default 0 isn't required
    - user if admin request data

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
## Reply
### create reply
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
### get replies
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

### Audit_logs
### Get logs (ADMIN)
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

## 🔐 Authentication & Authorization
- Use **JWT (JSON WEB TOKEN)** for authentication
- Tokens are parsed in the `Authorization` header as:

```http
Authorization: Bearer <access_token>
```

### Roles
- **USER:** can create/ update their tickets, send replies
- **ADMIN:** can update ticket status, reply to any ticket, delete replies, view audit logs

## 🤝 Contributing
 This project is open for suggestions and improvements. If you'd like to contribute, ``feel free to open issues or submit pull requests.``

## 📬 Contact
 `Feel free to check out the project, use it, and share feedback!`
- 💻 GitHub: [Abdelrahman Hossam](https://github.com/AbdelRahmanH1)
- 💼 LinkedIn: [Abdelrahman Hossam](https://www.linkedin.com/in/abdelrahmanh1/)
