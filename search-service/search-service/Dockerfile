# ---------- BUILD STAGE ----------
FROM maven:3.9.9-eclipse-temurin-22 AS build

# Workdir inside image
WORKDIR /build

# Copy pom + sources
COPY pom.xml .
COPY src ./src

# Build Quarkus app (JVM mode)
RUN mvn -q -DskipTests package

# ---------- RUNTIME STAGE ----------
FROM eclipse-temurin:22-jre-alpine

WORKDIR /work

# Copy Quarkus runnable jar + libs
# (this is the default layout when you run `mvn package` in Quarkus)
COPY --from=build /build/target/quarkus-app/lib/ /work/lib/
COPY --from=build /build/target/quarkus-app/*.jar /work/
COPY --from=build /build/target/quarkus-app/app/ /work/app/
COPY --from=build /build/target/quarkus-app/quarkus/ /work/quarkus/

# Change this if your search-service uses another port
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "/work/quarkus-run.jar"]