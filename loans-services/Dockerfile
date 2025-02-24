# Start with a base image containing Java runtime
# Use the Eclipse Temurin JDK 21 as the base image
FROM eclipse-temurin:21-jdk

# Add Maintainer Info
LABEL maintainer="wilfriedfotio55@gmail.com"
LABEL authors="F-Wilfried"

ENV SPRING_PROFILES_ACTIVE=docker

# Set the working directory inside the container
WORKDIR /app

# The application's jar file
COPY target/loans-services-0.0.1-SNAPSHOT.jar app.jar

# Make port 8086 available to the world outside this container
EXPOSE 8086


# Run the jar file
ENTRYPOINT ["java","-jar","app.jar"]
