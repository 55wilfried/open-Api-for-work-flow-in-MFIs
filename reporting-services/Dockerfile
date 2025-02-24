
FROM eclipse-temurin:21-jdk

# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"

ENV SPRING_PROFILES_ACTIVE=docker
# Set the working directory inside the container
WORKDIR /app


# The application's jar file
COPY target/reporting-services-0.0.1-SNAPSHOT.jar app.jar


# Make port 8085 available to the world outside this container
EXPOSE 8085

# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
