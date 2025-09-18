## Multi-stage Dockerfile for Render (repo root; app in gameclub/)

# Build stage
FROM maven:3.9.8-eclipse-temurin-21 AS build
WORKDIR /app

# Copy Maven project from subdirectory
COPY gameclub/pom.xml ./pom.xml
COPY gameclub/src ./src

# Build the jar (skip tests for faster builds)
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy built jar
COPY --from=build /app/target/gameclub-0.0.1-SNAPSHOT.jar app.jar

# Render provides PORT; pass it to Spring via -Dserver.port
ENV PORT=9090
EXPOSE 9090

ENV JAVA_OPTS=""
CMD ["sh", "-c", "java $JAVA_OPTS -Dserver.port=${PORT} -jar app.jar"]


