# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-22 AS build

# Workdir inside the image
WORKDIR /app

# Copy Maven descriptor first (better caching)
COPY pom.xml .

# Copy the rest of the source code
COPY src ./src

# Build the Spring Boot JAR (skip tests to speed up)
RUN mvn -q -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:22-jre-alpine

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Communication-service runs on 8082
EXPOSE 8082

# Optional: override datasource URL when running in Docker
# (you can remove this if you prefer application.properties)
# ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3307/journal?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC

ENTRYPOINT ["java", "-jar", "/app/app.jar"]