# =========
# 1. BUILD STAGE
# =========
FROM maven:3.9-eclipse-temurin-21 AS build

# Set working directory inside the container
WORKDIR /app

# Copy pom.xml first (better layer caching)
COPY pom.xml .

# Download dependencies (optional but speeds up repeated builds)
RUN mvn -q -DskipTests dependency:go-offline

# Copy source code
COPY src ./src

# Build the jar
RUN mvn -q -DskipTests package


# =========
# 2. RUNTIME STAGE
# =========
FROM eclipse-temurin:21-jre

# Set working dir in the runtime container
WORKDIR /app

# Copy the built jar from the build stage
# Adjust the JAR name if yours is different!
COPY --from=build /app/target/*.jar app.jar

# Expose the port your profile-service uses
# Change to whatever you have in application.properties (e.g. 8081)
EXPOSE 8081

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "/app/app.jar"]