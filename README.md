# Ticket Service

## Overview
The **Ticket Service** is a microservice within the Movie Reservation System responsible for managing movie tickets. It handles ticket creation, retrieval, and association with users, movies, and schedules. It also handles the payment process with Stripe and payment acknowledgment of payment with a webhook.

## Features
- Create new tickets for users upon successful payment.
- Retrieve ticket details based on user or movie.
- Associate tickets with seats, schedules, and reservations.

## Technologies Used
- **Spring Boot** – Core framework for building the microservice.
- **Spring Data JPA** – For interacting with the database.
- **MySQL** – Database for storing ticket-related information.
- **Feign Client** – For inter-service communication.
- **Spring Cloud Eureka** – Service discovery and registration.
- **JWT Authentication** – Securing API endpoints.

## API Endpoints

### Ticket Management
| Method | Endpoint | Description |
|--------|---------|-------------|
| `GET` | `/tickets` | Get all tickets |
| `GET` | `/tickets/{id}` | Get specific ticket |
| `GET` | `/tickets/user/{userId}` | Get all tickets of specific user |
| `GET` | `/tickets/movie/{movieId}` | Get all tickets of specific movie |
| `GET` | `/tickets/schedule/{scheduleId}` | Get all tickets of specific schedule |
| `POST` | `/tickets/create` | Create a new ticket |
| `POST` | `/tickets/multiple/create` | Create multiple tickets at once |
| `POST` | `/tickets/create-checkout-session` | Creates stripe checkout session to buy tickets |
| `DELETE` | `/tickets/delete/{id}` | Delete specific ticket |
| `POST` | `/webhook` | Listens for stripe events for after payment action is taken |

## Service Communication
- Communicates with **Seat Service** to update seat availability.
- Communicates with **Reservation Service** to associate tickets with reservations.

## Installation & Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/ChristosDurro/movie-reservation-system-ticket-service.git
   ```
2. Navigate to the project folder:
   ```bash
   cd movie-reservation-system-ticket-service
   ```
3. Configure the `application.properties` file:
   ```properties
   # DB configuration
   spring.datasource.url=jdbc:mysql://localhost:3306/ticket_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

   # Stripe api details
   stripe.api.key=your_stripe_api
   stripe.webhook.secret=your_webhook_secret

   # Eureka and port and other stuff details you may want to include
   ```
4. Build and run the service:
   ```bash
   mvn spring-boot:run
   ```

---

This service is part of the **Movie Reservation System**, designed to showcase a microservices-based architecture with Spring Boot.

