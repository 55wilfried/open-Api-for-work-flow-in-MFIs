# Start with a base image containing Java runtime
# Use the Eclipse Temurin JDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

# Create a non-root user for security
WORKDIR /app

COPY target/auth-services-0.0.1-SNAPSHOT.jar app.jar

# Make port 8081 available to the world outside this container
EXPOSE 8081


ENTRYPOINT ["java", "-jar", "app.jar"]
