# Start with a base image containing Java runtime
# Use the Eclipse Temurin JDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"
# Set environment variables
ENV SPRING_PROFILES_ACTIVE=docker

# Create a non-root user for security
RUN useradd -m appuser


RUN chown -R appuser:appuser /app

# Switch to the non-root user
USER appuser

# Set the working directory inside the container
WORKDIR /app
# The application's jar file
COPY target/register-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8761


# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
