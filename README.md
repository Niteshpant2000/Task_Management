## Project Setup Instructions

### Description

This project implements CRUD APIs for a Task Management System using Spring Boot and H2 Database.

**Features:**
- Full CRUD operations for tasks
- API documentation available via Swagger UI after starting the project
- Pessimistic locking on DB calls to ensure data consistency for all users
- Pagination and status-based filtering for the "get all tasks" API, with results sorted by due date
- Date validation: tasks can only be created or updated with future dates
- Mandatory fields: title and date; status defaults to `PENDING`
- Task IDs are auto-generated


### 1. Clone the repository
```
https://github.com/Niteshpant2000/Task_Management.git
cd taskmanagement
```

### 2. Install Java JDK 17 or higher
- Download and install Java from [Adoptium](https://adoptium.net/temurin/releases?version=17&os=any&arch=any)

### 3. (Optional) Install Gradle
- The project includes Gradle Wrapper (`gradlew`/`gradlew.bat`), so you do not need to install Gradle globally.

### 4. Database Setup
- By default, the project uses H2 (in-memory or file-based). No manual setup is needed.

### 5. Build the project
- On Windows:
	```
	.\gradlew.bat build
	```
- On Linux/macOS:
	```
	./gradlew build
	```

### 6. Run the application
- On Windows:
	```
	.\gradlew.bat bootRun
	```
- On Linux/macOS:
	```
	./gradlew bootRun
	```

### 7. Access the application
- Open your browser and go to:
	- http://localhost:9092

- H2 Console for database access:
    -url -> jdbc:h2:./database/taskmanagementdb
        -username -> admin
        -password -> password
	- http://localhost:9092/h2-console

### 8. Run tests
- On Windows:
	```
	.\gradlew.bat test
	```
- On Linux/macOS:
	```
	./gradlew test
	```

### 9. API Documentation
- You can interact with the API's using the below swagger url
	- http://localhost:9092/swagger-ui.html


