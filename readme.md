# SkiInstructorApp - Project Overview

## Introduction

SkiInstructorApp is a RESTful API designed for managing ski trips and ski instructors. Exam project for backend 2025

---

## Project Structure

The project follows the MVC (Model-View-Controller) architecture and consists of the following main components:

### Main Packages

- `config`: Configuration classes for setting up the application, including Hibernate and Routing.
- `controllers`: Controllers that handle HTTP requests related to trips, instructors, and authedication.
- `entities`: Entity classes representing the database tables (e.g., Trip, Instructor).
- `exceptions`: Custom exception classes for error handling.
- `middleware`: User role handling to secure protected endpoints.
- `persistence`: 
  -   `dao`: Data Access Objects for interacting with the database.
  - `dto`: Data Transfer Objects for exchanging data between layers.
- `utils`: Utility classes to handle common operations.


---

## Technologies Used

- Java 17 — Programming language
- Maven — Build automation and dependency management
- Javalin — Lightweight web framework for building RESTful APIs
- Hibernate ORM — Object-relational mapping for database interaction
- PostgreSQL — Relational database
- Lombok — Reduces boilerplate code (getters, setters, constructors)
- Jackson — JSON processing and serialization
- Java JWT (Auth0) — JSON Web Token generation and validation
- JBCrypt — Password hashing for user authentication
- SLF4J & slf4j-simple — Logging framework
- JUnit 5 — Unit testing framework
- RestAssured — API testing framework
- TestContainers — Containerized database testing
- Hamcrest — Matcher library for writing readable assertions in tests
- jcovalent-junit-logging — Enhanced JUnit test logging


---

## Getting Started

### 1. Clone this repository

### 2. Configure Database Settings

Edit the config.properties file in the resources directory with the following content:

DB_NAME=your_db_name
DB_USERNAME=postgres
DB_PASSWORD=your_password

Note: Replace the placeholders with your actual configuration values.

### 3. API Endpoints

POST /instructors – Create a new instructor

GET /instructors – Get all instructors

PUT /instructors/{instructorId} – Update instructor by ID

DELETE /instructors/{instructorId} – Delete instructor by ID

GET /skilessons – Get all ski lessons

GET /skilessons/{skilessonId} – Get ski lesson by ID

POST /skilessons – Create a new ski lesson

PUT /skilessons/{skilessonId} – Update ski lesson by ID

DELETE /skilessons/{skilessonId} – Delete ski lesson by ID

PUT /skilessons/{skilessonId}/instructors/{instructorId} – Assign instructor to lesson

GET /skilesson/instructor/{instructorId} – Get lessons by instructor ID

GET /ski-lessons/level/{level} – Get lessons by skill level

🔐 Authentication

POST /login – Authenticate user and return JWT token

🔒 Admin-only Endpoints (require JWT token)

POST /createskilessons – Create a ski lesson (admin only)

PUT /updateskilessons/{skilessonId} – Update a ski lesson (admin only)

DELETE /deleteskilesson/{skilessonId} – Delete a ski lesson (admin only)

Note there is no populator route, as it runs when the application starts, from the same method as the RoutesTest class, to ensure they are both using the exact same data set

### Endpoint testing

In the resources folder, a dev.http file is located, which outputs the following when run:


Testing started at 14.11 ...
POST http://localhost:7000/login
Content-Type: application/json

{
"username": "admin",
"password": "admin123"
}

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 179


Response file saved.
> 2025-04-07T141147.200.json

Response code: 200 (OK); Time: 66ms (66 ms); Content length: 179 bytes (179 B)

Token fetched successfully.
Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTc0NDAyNzkwNywiZXhwIjoxNzQ0MTE0MzA3fQ.9NduitbepluVC6KOZH3--9S7U1q-Fd2c-tvvBXVmykQ

Script finished
GET http://localhost:7000/skilesson/instructor/1

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 206


Response file saved.
> 2025-04-07T141147-1.200.json

Response code: 200 (OK); Time: 151ms (151 ms); Content length: 206 bytes (206 B)


GET http://localhost:7000/skilessons

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 206


Response file saved.
> 2025-04-07T141147-2.200.json

Response code: 200 (OK); Time: 12ms (12 ms); Content length: 206 bytes (206 B)


GET http://localhost:7000/skilessons/level/BEGINNER

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 206


Response file saved.
> 2025-04-07T141147-3.200.json

Response code: 200 (OK); Time: 10ms (10 ms); Content length: 206 bytes (206 B)


GET http://localhost:7000/skilessons/2

HTTP/1.1 404 Not Found
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 72


Response file saved.
> 2025-04-07T141147.404.json

Response code: 404 (Not Found); Time: 10ms (10 ms); Content length: 72 bytes (72 B)


POST http://localhost:7000/createskilessons
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTc0NDAyNzkwNywiZXhwIjoxNzQ0MTE0MzA3fQ.9NduitbepluVC6KOZH3--9S7U1q-Fd2c-tvvBXVmykQ
Content-Type: application/json

{
"name": "Advanced Carving",
"startDateTime": "2025-04-10T09:00:00",
"endDateTime": "2025-04-10T11:00:00",
"latitude": 60.3913,
"longitude": 5.3221,
"price": 599.99,
"skillLevel": "ADVANCED",
"instructorId": 1
}

HTTP/1.1 201 Created
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 13


Response file saved.
> 2025-04-07T141147.201.json

Response code: 201 (Created); Time: 41ms (41 ms); Content length: 13 bytes (13 B)


PUT http://localhost:7000/updateskilessons/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTc0NDAyNzkwNywiZXhwIjoxNzQ0MTE0MzA3fQ.9NduitbepluVC6KOZH3--9S7U1q-Fd2c-tvvBXVmykQ
Content-Type: application/json

{
"name": "Beginner's Paradise"
}

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 15


Response file saved.
> 2025-04-07T141147-4.200.json

Response code: 200 (OK); Time: 17ms (17 ms); Content length: 15 bytes (15 B)


PUT http://localhost:7000/skilessons/1/instructors/2

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 29


Response file saved.
> 2025-04-07T141147-5.200.json

Response code: 200 (OK); Time: 12ms (12 ms); Content length: 29 bytes (29 B)


POST http://localhost:7000/instructors
Content-Type: application/json

{
"firstName": "Anna",
"lastName": "Berg",
"email": "anna.berg@example.com",
"phone": "98765432",
"experience": 6
}

HTTP/1.1 201 Created
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 13


Response file saved.
> 2025-04-07T141147-1.201.json

Response code: 201 (Created); Time: 9ms (9 ms); Content length: 13 bytes (13 B)


GET http://localhost:7000/instructors

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 358


Response file saved.
> 2025-04-07T141147-6.200.json

Response code: 200 (OK); Time: 13ms (13 ms); Content length: 358 bytes (358 B)


PUT http://localhost:7000/instructors/1
Content-Type: application/json

{
"firstName": "John",
"lastName": "Updated",
"email": "john.updated@example.com",
"phone": "00000000",
"experience": 10
}

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 15


Response file saved.
> 2025-04-07T141147-7.200.json

Response code: 200 (OK); Time: 11ms (11 ms); Content length: 15 bytes (15 B)


DELETE http://localhost:7000/instructors/1

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 15


Response file saved.
> 2025-04-07T141147-8.200.json

Response code: 200 (OK); Time: 21ms (21 ms); Content length: 15 bytes (15 B)


DELETE http://localhost:7000/deleteskilesson/1
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOiJhZG1pbiIsImlhdCI6MTc0NDAyNzkwNywiZXhwIjoxNzQ0MTE0MzA3fQ.9NduitbepluVC6KOZH3--9S7U1q-Fd2c-tvvBXVmykQ

HTTP/1.1 200 OK
Date: Mon, 07 Apr 2025 12:11:47 GMT
Content-Type: application/json
Content-Length: 15


Response file saved.
> 2025-04-07T141148.200.json

Response code: 200 (OK); Time: 11ms (11 ms); Content length: 15 bytes (15 B)


