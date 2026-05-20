# Use Eclipse Temurin JDK 22 on Alpine Linux as the base image for a lightweight runtime
FROM eclipse-temurin:25-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the compiled JAR file from the host's target folder to the container
COPY target/RESTful_API-0.0.1-SNAPSHOT.jar app.jar

# Inform Docker that the container listens on port 8080 at runtime
EXPOSE 8080

# Define the command to execute the application
ENTRYPOINT ["java", "-jar", "app.jar"]