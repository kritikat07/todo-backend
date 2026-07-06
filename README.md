# Todo Backend (Spring Boot + JWT)

A simple Todo application backend with JWT authentication. Each user can only see/manage their own todos.

## Tech Stack
- Java 17, Spring Boot 3.3.2
- Spring Security + JWT (jjwt 0.11.5)
- Spring Data JPA + MySQL
- Lombok

## Setup

1. **Create the database** (or let it auto-create — `createDatabaseIfNotExist=true` is already set):
   ```sql
   CREATE DATABASE todo_db;
   ```

2. **Update `src/main/resources/application.properties`** with your MySQL username/password.

3. **Run the app**:
   ```bash
   mvn spring-boot:run
   ```
   Server starts at `http://localhost:8080`.

## API Endpoints

### Auth (public — no token needed)
| Method | Endpoint             | Body                                          |
|--------|----------------------|------------------------------------------------|
| POST   | `/api/auth/register`  | `{ "username", "email", "password" }`          |
| POST   | `/api/auth/login`     | `{ "username", "password" }`                   |

Both return: `{ "token", "username", "message" }`

### Todos (protected — needs header `Authorization: Bearer <token>`)
| Method | Endpoint                      | Description                     |
|--------|-------------------------------|----------------------------------|
| POST   | `/api/todos`                  | Create a todo                    |
| GET    | `/api/todos`                  | Get all todos of logged-in user  |
| GET    | `/api/todos/{id}`              | Get a single todo                |
| PUT    | `/api/todos/{id}`              | Update a todo                    |
| PATCH  | `/api/todos/{id}/complete`     | Mark todo as completed/read      |
| DELETE | `/api/todos/{id}`              | Delete a todo                    |

### Sample flow
1. Register → get token.
2. Add `Authorization: Bearer <token>` header on all `/api/todos/**` calls.
3. Create/update/delete/mark-complete your todos.

## Notes
- Passwords are hashed with BCrypt before storing.
- JWT secret + expiry are in `application.properties` (`jwt.secret`, `jwt.expiration.ms`) — change the secret before deploying anywhere real.
- Each todo is tied to `ownerUsername`, so users can't see or edit each other's todos.
