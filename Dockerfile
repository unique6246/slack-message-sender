# Use an official OpenJDK runtime as a parent image
FROM openjdk:17

# Set the working directory inside the container
WORKDIR /app

# Copy the project's JAR file into the container at /app
COPY target/Assignment-0.0.1-SNAPSHOT.jar /app/slack-message-sender.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "slack-message-sender.jar"]
