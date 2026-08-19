# Real-Time Customer Behavior Analytics Platform

This repo contains 3 backend microservices:

1. `UserService` (JWT auth)
2. `ActivityService` (Kafka producer)
3. `AnalyticsService` (Kafka consumer + analytics storage)

## Tech Stack

- Java 17
- Spring Boot 3.5
- Spring Security + JWT
- Spring Kafka
- Spring Data JPA
- H2 (default) and MySQL (optional)

## Project Structure

```text
real-time-customer-behavior-analytics
├── UserService
├── ActivityService
├── AnalyticsService
└── docker-compose.yml
```

## Run Order (important)

1. Start Kafka (and optional MySQL) using Docker.
2. Start `UserService` on port `8080`.
3. Start `ActivityService` on port `8081`.
4. Start `AnalyticsService` on port `8082`.

## 1) Start Infrastructure

From repo root:

```bash
docker compose up -d
```

Kafka bootstrap server: `localhost:9092`

## 2) Run Services

From each service directory:

```bash
mvn spring-boot:run
```

Or from root:

```bash
mvn -f UserService/pom.xml spring-boot:run
mvn -f ActivityService/pom.xml spring-boot:run
mvn -f AnalyticsService/pom.xml spring-boot:run
```

## 3) API Testing Flow

### User Service (`http://localhost:8080`)

Register:

```http
POST /auth/register
Content-Type: application/json

{
  "username": "aishwarya",
  "email": "aish@gmail.com",
  "password": "1234"
}
```

Login:

```http
POST /auth/login
Content-Type: application/json

{
  "username": "aishwarya",
  "password": "1234"
}
```

Response includes `token`.

Protected endpoint:

```http
GET /users/me
Authorization: Bearer <token>
```

### Activity Service (`http://localhost:8081`)

Publish user activity:

```http
POST /activities
Content-Type: application/json

{
  "userId": 1,
  "username": "aishwarya",
  "action": "PAGE_VIEW",
  "page": "/products",
  "metadata": {
    "device": "mobile",
    "sessionId": "S-101"
  }
}
```

### Analytics Service (`http://localhost:8082`)

Get all consumed events:

```http
GET /analytics
```

Get events by username:

```http
GET /analytics/user/aishwarya
```

Get action summary:

```http
GET /analytics/summary/actions
```

## Database Notes

- Default config uses in-memory H2 for `UserService` and `AnalyticsService`.
- To use MySQL, uncomment MySQL properties in each service `application.properties`.
- `docker-compose.yml` includes a MySQL container on port `3306`.

## Health Endpoints

- `http://localhost:8080/users/health`
- `http://localhost:8081/activities/health`
- `http://localhost:8082/analytics/health`


