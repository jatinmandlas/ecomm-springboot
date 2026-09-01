# E-Commerce Backend

A REST API for an e-commerce application built with Spring Boot.

## Features

- User registration and login
- JWT authentication
- Product management
- Category management
- Shopping cart
- Cart items
- Product search
- Pagination and sorting
- Admin/product management

## Tech Stack

- Java
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT
- MySQL
- ModelMapper
- Maven

## Project Structure

src/
├── controller/
├── service/
├── repository/
├── model/
├── Payload/
├── security/
└── exceptions/

## Environment Variables

Create a `.env` file or configure the following environment variables:

DB_URL,
DB_USERNAME,
DB_PASSWORD,
JWT_SECRET,
JWT_EXPIRATION_MS,
JWT_COOKIE_NAME,

## Running Locally

1. Clone the repository
2. Configure the environment variables
3. Create the MySQL database
4. Run the application with Maven
5. Open the API



## Future Improvements

-Microservices
-Reddis and Kafka
- Email notifications
- Docker deployment
