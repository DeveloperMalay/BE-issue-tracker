FROM node:22-alpine

LABEL authors="Malay Pandit"

# Set the working directory inside the container
WORKDIR /app

# Copy the application JAR file to the container
COPY build/libs/IssueTracker-0.0.1-SNAPSHOT-plain.jar  /app/issue-tracker.jar

# Expose the port on which the application will run
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/issue-tracker.jar"]
