# Use an official OpenJDK runtime as a base image
FROM openjdk:22-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Download the JAR file from the provided link
ADD https://mega.nz/file/cvlzXALL#BTI38of0_Ggzgjv0rXumvospy0jrUZXozsuNYONgLn8 app.jar

# Expose the port your Spring Boot application runs on
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
