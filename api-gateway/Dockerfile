# Use a smaller, optimized Java runtime image (JRE, not JDK)
FROM eclipse-temurin:21-jre

# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"

# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

# Create a non-root user for security
RUN useradd -m appuser

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file into the container
COPY target/api-gateway-0.0.1-SNAPSHOT.jar app.jar

# Change ownership of the app to the non-root user
RUN chown -R appuser:appuser /app

# Switch to the non-root user
USER appuser

# Expose the port your application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
