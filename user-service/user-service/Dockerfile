# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /project

# Copy pom + sources
COPY pom.xml .
COPY src ./src

# Build the jar
RUN mvn package -DskipTests

# ===== Run stage =====
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar from build stage (adjust pattern if needed)
COPY --from=build /project/target/*.jar app.jar

# Expose the port the service listens on (8080 is common; change if needed)
EXPOSE 8080

# Run the service
ENTRYPOINT ["java", "-jar", "app.jar"]