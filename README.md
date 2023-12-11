# Task Management System

Task Management System is a Spring Boot application designed to facilitate task tracking and management. It offers a robust set of features for creating, updating, and monitoring tasks, as well as managing user authentication.

## Features

- **Task Management:**
  - Create a new task: `POST /api/tasks`
  - Edit an existing task: `PUT /api/tasks/{taskId}`
  - View an existing task: `GET /api/tasks/{taskId}`
  - Delete a task: `DELETE /api/tasks/{taskId}`
  - Change the status of a task: `PATCH /api/tasks/{taskId}/status`
  - Assign an executor to a task: `PUT /api/tasks/{taskId}/assignee`

- **Comments:**
  - View comments for a task: `GET /api/tasks/{taskId}/comments`
  - Add a comment to a task: `POST /api/tasks/{taskId}/comments`

- **User Management:**
  - View tasks assigned to an executor: `GET /api/users/{userId}/assigned-tasks`
  - Get tasks for a specific author: `GET /api/users/{userId}/authored-tasks`

- **Authentication:**
  - Register or authenticate: `POST /api/v1/auth/signing`
  - Update token: `POST /api/v1/auth/refresh`

## Getting Started

### Prerequisites

- Java 17
- Gradle for building the project
- Docker and Docker Compose for running the application in a container

### Dependencies 

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-data-rest'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.boot:spring-boot-starter-validation'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-log4j2'
    implementation 'org.liquibase:liquibase-core'
    implementation 'io.jsonwebtoken:jjwt-api:0.11.5'
    implementation 'com.google.code.findbugs:jsr305:3.0.2'
    implementation 'org.mapstruct:mapstruct:1.5.5.Final'
    implementation 'org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0'

    compileOnly 'org.projectlombok:lombok'
    runtimeOnly 'io.jsonwebtoken:jjwt-impl:0.11.5'
    runtimeOnly 'io.jsonwebtoken:jjwt-jackson:0.11.5'
    runtimeOnly 'org.springframework.boot:spring-boot-devtools'
    runtimeOnly 'org.postgresql:postgresql'
    annotationProcessor 'org.mapstruct:mapstruct-processor:1.5.5.Final'
    annotationProcessor 'org.projectlombok:lombok-mapstruct-binding:0.2.0'
    annotationProcessor 'org.projectlombok:lombok:1.18.20'

    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.security:spring-security-test'
}
```

### Build and Run Locally

1. Clone the repository:

   ```bash
   git clone <repository-url>
   ```

2. Navigate to the project directory:

   ```bash
   cd task-management-system
   ```

3. Build the project:

   ```bash
   ./gradlew clean build
   ```

4. Run the application locally:

   ```bash
   ./gradlew bootRun
   ```

   The application will be accessible at [http://localhost:8080](http://localhost:8080).

### Run in Docker

1. Build the Docker image:

   ```bash
   ./gradlew clean build
   ```

2. Start the application using Docker Compose:

   ```bash
   docker-compose up
   ```

   The application will be accessible at [http://localhost:8080](http://localhost:8080).

### Documentation

- Explore the API using the provided Postman collection: [task-management-system.postman_collection.json](src/main/resources/task-management-system.postman_collection.json).
- Access Swagger-UI for API documentation: [http://localhost:8080/swagger-ui/index.html#](http://localhost:8080/swagger-ui/index.html#).

## Configuration

- Profiles: The application supports two profiles, `dev` and `prod`. The default is `dev`, and `prod` is activated when running in Docker. Update credentials in `application-prod.yml` and `application-dev.yml` if needed.

- Logging: In `prod` profile, the log level is set to Warn by default. Modify settings in the log4j2 configuration as required.

- Security: The application uses JWT tokens for authentication. Adjust token duration settings in `src/main/resources/defaults/jwt.yml`.

## Thanks

- Many thanks to Effective Mobile for providing an interesting task