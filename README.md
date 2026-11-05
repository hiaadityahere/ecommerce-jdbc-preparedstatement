# E-Commerce Application – JDBC

A database-driven E-Commerce application developed using Core Java and JDBC. The project focuses on implementing database operations, transaction-oriented application logic, and efficient SQL execution using JDBC.

## Features

- **Product Management:** Perform CRUD operations on products.
- **Customer Management:** Manage customer-related information.
- **Admin/Seller Management:** Handle seller-related operations.
- **Order Management:** Manage customer orders and order-related operations.
- **Database Connectivity:** Establish database connections using JDBC.
- **PreparedStatement:** Execute parameterized SQL queries securely and efficiently.
- **Batch Processing:** Execute multiple SQL operations using JDBC batch processing.
- **DTO Pattern:** Use Data Transfer Objects to transfer data between application layers.
- **Exception Handling:** Handle application-specific exceptions and database-related errors.

## Architecture

The application follows a three-layer architecture to maintain separation of concerns.

| Layer | Responsibility |
|---|---|
| Model | Represents application entities and their data. |
| Repository | Handles database connectivity and SQL operations. |
| Service | Implements business logic, validations, and application workflows. |

DTOs are used to transfer data between layers, particularly for Customer and Admin/Seller operations.

## Technology Stack

- Java (Core Java)
- JDBC (Java Database Connectivity)
- MySQL / MariaDB
- PreparedStatement
- JDBC Batch Processing
- Maven
- Eclipse IDE
- Git & GitHub

## Database

The application uses a relational database for persistent data storage.

The `db_query.sql` file contains the SQL scripts required for database and table setup.

## Getting Started

1. Clone the repository:

   ```bash
   git clone https://github.com/hiaadityahere/ecommerce-jdbc-preparedstatement
   ```

2. Create the required database using the SQL scripts in `db_query.sql`.
3. Configure the database connection properties in the application.
4. Import the project into Eclipse as an existing Maven project.
5. Resolve Maven dependencies.
6. Run the application through its main class.

## Project Purpose

This project was developed to gain practical experience with JDBC, SQL operations, PreparedStatement, batch processing, DTOs, Maven, and layered application architecture.