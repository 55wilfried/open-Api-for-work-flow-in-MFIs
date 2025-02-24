# Start with a base image containing Java runtime
# Use the Eclipse Temurin JDK 21 as the base image
FROM eclipse-temurin:21-jdk

ENV SPRING_PROFILES_ACTIVE=docker


# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"
# Set the working directory inside the container
WORKDIR /app


# Make port 8082 available to the world outside this container
# The application's jar file
COPY target/client-services-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8082
# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
