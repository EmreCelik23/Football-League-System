# Football League System

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/Auth-JWT-black?style=for-the-badge&logo=jsonwebtokens)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

Football League System is a Spring Boot backend API for managing a football league. It provides REST endpoints for teams, players, matches, lineups, match events, standings, team statistics, player statistics, and JWT-based authentication.

## Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Authentication](#authentication)
- [API Endpoints](#api-endpoints)
- [Example Requests](#example-requests)
- [Useful Commands](#useful-commands)
- [Notes](#notes)

## Features

- Create, list, update, and delete teams
- Create, list, update, and delete players
- Add multiple players in a single request
- Create, update, start, finish, and delete matches
- Set match lineups with starting players and reserves
- Add match events such as goals, assists, yellow cards, red cards, and substitutions
- Automatically update scores, player statistics, team statistics, and standings
- Secure endpoints with JWT authentication
- Layered architecture with Controller, Service, Repository, DTO, and Mapper layers
- Request validation and global exception handling

## Tech Stack

| Technology | Usage |
| --- | --- |
| Java 17 | Main programming language |
| Spring Boot 3.2.0 | Backend application framework |
| Spring Web | REST API |
| Spring Data JPA | Database access |
| Spring Security | Authentication and authorization |
| JWT | Stateless authentication tokens |
| PostgreSQL | Relational database |
| Lombok | Boilerplate code reduction |
| MapStruct | Entity and DTO mapping |
| Maven | Build and dependency management |

## Project Structure

```text
src/main/java/com/ytu/sad
├── controller          # REST controllers
├── service             # Service interfaces and business contracts
├── service/impl        # Service implementations
├── persistence
│   ├── dto             # Data transfer objects
│   ├── entity          # JPA entities
│   ├── enums           # Enum definitions
│   ├── mapper          # MapStruct mappers
│   └── repository      # Spring Data JPA repositories
├── security            # JWT and Spring Security configuration
└── exception           # Global exception handling
```

## Getting Started

### Requirements

- Java 17+
- PostgreSQL
- Maven or the included Maven Wrapper

### 1. Clone the repository

```bash
git clone https://github.com/<username>/Football-League-System.git
cd Football-League-System
```

### 2. Create the PostgreSQL database

```sql
CREATE DATABASE football_league;
```

The application uses the following database configuration by default:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/football_league
spring.datasource.username=postgres
server.port=8080
```

Update `src/main/resources/application.properties` according to your local PostgreSQL username and password.

> Note: The project currently uses `spring.jpa.hibernate.ddl-auto=none`, so the database tables must already exist. For local development, you can temporarily change this value to `update` if needed.

### 3. Run the application

```bash
./mvnw spring-boot:run
```

For Windows:

```bash
mvnw.cmd spring-boot:run
```

The API runs at:

```text
http://localhost:8080
```

## Authentication

The `/api/v1/auth/**` endpoints are public. All other endpoints require a JWT token returned from the register or login endpoint.

```http
Authorization: Bearer <token>
```

### Auth Endpoints

| Method | Endpoint | Description |
| --- | --- | --- |
| POST | `/api/v1/auth/register` | Register a new user |
| POST | `/api/v1/auth/login` | Log in and receive a JWT token |

## API Endpoints

### Team

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/v1/team` | List all teams |
| GET | `/api/v1/team/{teamId}` | Get team details |
| GET | `/api/v1/team/{teamId}/players` | List players of a team |
| GET | `/api/v1/team/{teamId}/matches` | List matches of a team |
| POST | `/api/v1/team` | Create a team |
| PUT | `/api/v1/team/{teamId}` | Update a team |
| DELETE | `/api/v1/team/{teamId}` | Delete a team |

### Player

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/v1/player` | List all players |
| GET | `/api/v1/player/{playerId}` | Get player details |
| POST | `/api/v1/player` | Create a player |
| POST | `/api/v1/player/batch` | Create multiple players |
| PUT | `/api/v1/player/{playerId}` | Update a player |
| DELETE | `/api/v1/player/{playerId}` | Delete a player |

### Match

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/v1/match` | List all matches |
| GET | `/api/v1/match/{matchId}` | Get match details |
| POST | `/api/v1/match` | Create a match |
| PUT | `/api/v1/match/{matchId}` | Update a match |
| DELETE | `/api/v1/match/{matchId}` | Delete a match |
| POST | `/api/v1/match/{matchId}/lineups` | Set match lineups |
| GET | `/api/v1/match/{matchId}/lineups` | Get match lineups |
| POST | `/api/v1/match/{matchId}/start` | Start a match |
| POST | `/api/v1/match/{matchId}/finish` | Finish a match |
| POST | `/api/v1/match/{matchId}/goal` | Add a goal event |
| DELETE | `/api/v1/match/{matchId}/goal/{goalId}` | Delete a goal event |
| POST | `/api/v1/match/{matchId}/card` | Add a card event |
| DELETE | `/api/v1/match/{matchId}/card/{cardId}` | Delete a card event |
| POST | `/api/v1/match/{matchId}/substitution` | Add a substitution event |
| DELETE | `/api/v1/match/{matchId}/substitution/{substitutionId}` | Delete a substitution event |
| POST | `/api/v1/match/reset` | Reset all matches |

### Statistics and Standings

| Method | Endpoint | Description |
| --- | --- | --- |
| GET | `/api/v1/standings` | List league standings |
| GET | `/api/v1/standings/{teamId}` | Get standings details for a team |
| DELETE | `/api/v1/standings/{teamId}` | Delete standings record for a team |
| POST | `/api/v1/standings/reset` | Reset standings |
| GET | `/api/v1/team-stats` | List team statistics |
| GET | `/api/v1/team-stats/{teamId}` | Get statistics for a team |
| DELETE | `/api/v1/team-stats/{teamId}` | Delete statistics for a team |
| POST | `/api/v1/team-stats/reset` | Reset team statistics |
| GET | `/api/v1/player-stats` | List player statistics |
| GET | `/api/v1/player-stats/{playerId}` | Get statistics for a player |
| DELETE | `/api/v1/player-stats/{playerId}` | Delete statistics for a player |
| POST | `/api/v1/player-stats/reset` | Reset player statistics |

## Example Requests

### Register

```bash
curl -X POST http://localhost:8080/api/v1/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Emre",
    "lastName": "Yilmaz",
    "username": "emre",
    "password": "123456",
    "role": "USER",
    "favTeamId": null
  }'
```

### Login

```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "emre",
    "password": "123456"
  }'
```

### Create a Team

```bash
curl -X POST http://localhost:8080/api/v1/team \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "name": "YTU FC",
    "city": "Istanbul",
    "stadium": "Davutpasa Arena",
    "foundedYear": 1911,
    "owner": "YTU",
    "manager": "Emre"
  }'
```

### Create a Player

```bash
curl -X POST http://localhost:8080/api/v1/player \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "name": "Arda Yilmaz",
    "age": 22,
    "position": "CM",
    "role": "NOT_IN_SQUAD",
    "nationality": "Turkey",
    "teamId": 1,
    "number": 8
  }'
```

### Create a Match

```bash
curl -X POST http://localhost:8080/api/v1/match \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "homeTeamId": 1,
    "awayTeamId": 2,
    "referee": "Ali Kaya",
    "date": "2026-07-01T20:00:00",
    "stadium": "Davutpasa Arena",
    "homeScore": 0,
    "awayScore": 0,
    "status": "SCHEDULED"
  }'
```

### Add a Goal

```bash
curl -X POST http://localhost:8080/api/v1/match/1/goal \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <token>" \
  -d '{
    "matchId": 1,
    "teamId": 1,
    "playerId": 10,
    "assistPlayerId": 8,
    "minute": 34
  }'
```

## Useful Commands

```bash
# Run the application
./mvnw spring-boot:run

# Run tests
./mvnw test

# Build the project
./mvnw clean package
```

## Notes

- Match statuses: `SCHEDULED`, `IN_PROGRESS`, `FINISHED`
- Player positions: `GK`, `CB`, `LB`, `RB`, `CDM`, `CM`, `CAM`, `LW`, `RW`, `ST`
- Player roles: `STARTING`, `RESERVE`, `NOT_IN_SQUAD`
- Goal, card, and substitution events can only be added while a match is `IN_PROGRESS`.
- A match can only be started when both teams have 11 starting players.
