# qp-assessment
qp-assessment for Application ID : 29564048


# **Grocery Booking API**

## **Overview**

The Grocery Booking API is a Spring Boot application that provides a system for managing grocery items, inventory, and user orders. It includes role-based access control with Spring Security and utilizes Docker for containerization.

## **Features**

User registration and authentication
Role-based access (Admin and User)
Grocery item management
Inventory management
Order placement
API documentation via Swagger
Secured endpoints using Basic Authentication
Dockerized deployment with Docker Compose

## **Technologies Used**

Spring Boot (Spring MVC, Spring Data JPA, Spring Security)
MySQL (Database)
Docker & Docker Compose
Swagger (Springdoc OpenAPI)
Lombok
Maven

## **Prerequisites**

### Ensure you have the following installed:

Java 17+
Maven
Docker
MySQL

## **Running the Application**

### 1. Clone the Repository

git clone https://github.com/your-repo/grocery-booking-api.git
cd grocery-booking-api

### 2. Set Up the Database

Update application.properties with your MySQL credentials:

spring.datasource.url=jdbc:mysql://localhost:3306/grocery_db
spring.datasource.username=root
spring.datasource.password=yourpassword

Create the database manually:

CREATE DATABASE grocery_db;

### 3. Run Using Docker Compose
Ensure Docker is running, then execute:
docker-compose up --build

This will start the application along with MySQL in containers.

### 4. Run Manually (Without Docker)

mvn clean install
mvn spring-boot:run

## API Endpoints

Public Endpoints (No Authentication Required)
User Registration: POST /register
Admin Endpoints (Requires Basic Auth)
Add Grocery Item: POST /admin/create/grocery-item
Update Grocery Item: PUT /admin/update/grocery-item
Delete Grocery Item: DELETE /admin/delete/grocery-item/{name}
Get All Grocery Items: GET /admin/getAll/grocery-items
Manage Inventory: PUT /admin/update-inventory
Get All Inventory: GET /admin/getAll/inventory
User Endpoints (Requires Authentication)
Place Order: POST /user/place-order
Get All Grocery Items: GET /user/getAll/grocery-items

## **Swagger API Documentation**

After running the application, access Swagger UI at:
http://localhost:8080/swagger-ui.html

## **Docker Configuration**

refer docker-compose.yml

networks:
  grocery-net:

volumes:
  mysql-data:

```
## **Authentication & Authorization**

The API uses Basic Authentication.
Admin and User roles are assigned at registration.
Admins can manage grocery items and inventory.
Users can place orders.


