# Resilient Backend API

## Overview

This project is a resilient backend for a Twitter-like application, developed in Java using Spring Boot 3. It provides a set of APIs for managing users, tweets, and other related functionalities. The application creates tables in a PostgreSQL database upon startup if they do not exist, or uses existing tables if they are already present.

## Prerequisites

Make sure you have the following prerequisites installed on your system:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/): for this project, Maven 3.8.6 was used.

## Getting Started

To run the application, follow these steps:
1. **src > main > resources > dummy_application-properties:** a dummy application.properties is presented here. Create your own application.properties next to this file, modifying the database properties (name, port, user, password).
2. Open a terminal and navigate to the project root directory.
3. Run the following Maven command to clean, install dependencies, and update snapshots:

    ```bash
    mvn clean install -U
    ```

4. After successful installation, run the application using the following command:

    ```bash
    ./mvnw spring-boot:run
    ```

The application will start, and you can access the Swagger documentation at [http://localhost:8080/swagger.html](http://localhost:8080/swagger.html).

## Project Structure

The project follows a specific structure:

- **src > main > ... > resilientbackend > controllers:** Contains controllers for users, tweets, etc., defining endpoints and methods (POSTs, GETs, etc.).

- **src > main > ... > resilientbackend > managers:** Houses managers for users, tweets, etc., defining classes for objects stored in the database.

- **src > main > ... > resilientbackend > DatabaseConnector.java:** Defines the connection to the PostgreSQL database.

- **src > main > ... > resilientbackend > ResilientBackendApplication.java:** Serves as the starting point of the application, with the `application.run(ResilientBackendApplication.class, args)` method.

- **src > main > ... > resilientbackend > exceptions:** Contains the exceptions definitions for each of the classes used along the application.

- **src > main > ... > resilientbackend > advicers:** Contains the handlers for each of the defined exceptions.

- **src > main > ... > resilientbackend > assemblers:** Houses classes to create EntityModels in a cleaner way.

## Database Initialization

Upon application startup, the system checks whether the required tables exist in the PostgreSQL database. If they do not exist, the tables are created; otherwise, the existing tables are utilized.

## Swagger Documentation

Swagger documentation for the API can be accessed at [http://localhost:8080/swagger.html](http://localhost:8080/swagger.html).

Feel free to explore the API and interact with the various endpoints provided.

**Note:** Ensure that the PostgreSQL database is properly configured and running before starting the application.
