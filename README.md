# Restaurant API

This is a Spring Boot application that provides a RESTful API for managing restaurants, menu items, and orders.

## Features

*   **Restaurant Management:** CRUD operations for restaurants.
*   **Menu Item Management:** CRUD operations for menu items, associated with a restaurant.
*   **Order Management:** Create, read, update, and delete orders.

## Technologies Used

*   Java 17
*   Spring Boot
*   Spring Data JPA
*   Maven
*   MySQL (for development)
*   PostgreSQL (for production)

## Getting Started

### Prerequisites

*   Java 17 or later
*   Maven
*   MySQL Server

### Setup and Run

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/restaurant-api.git
    cd restaurant-api
    ```

2.  **Configure the database:**

    Open `src/main/resources/application.properties` and update the following properties to match your local MySQL setup:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/food_delivery_dev?createDatabaseIfNotExist=true
    spring.datasource.username=your-mysql-username
    spring.datasource.password=your-mysql-password
    ```

3.  **Run the application:**
    ```bash
    ./mvnw spring-boot:run
    ```
    The application will start on port 8080.

## API Endpoints

The following are the available API endpoints:

### Restaurant Controller

*   `GET /restaurants`: Get all restaurants.
*   `GET /restaurants/{id}`: Get a restaurant by ID.
*   `POST /restaurants`: Create a new restaurant.
*   `PUT /restaurants/{id}`: Update a restaurant.
*   `DELETE /restaurants/{id}`: Delete a restaurant.

### Menu Item Controller

*   `GET /restaurants/{restaurantId}/menu-items`: Get all menu items for a restaurant.
*   `GET /menu-items/{id}`: Get a menu item by ID.
*   `POST /restaurants/{restaurantId}/menu-items`: Create a new menu item for a restaurant.
*   `PUT /menu-items/{id}`: Update a menu item.
*   `DELETE /menu-items/{id}`: Delete a menu item.

### Order Controller

*   `GET /orders`: Get all orders.
*   `GET /orders/{id}`: Get an order by ID.
*   `POST /orders`: Create a new order.
*   `PUT /orders/{id}`: Update an order.
*   `DELETE /orders/{id}`: Delete an order.
