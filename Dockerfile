FROM gradle:8.7-jdk17-alpine AS builder
WORKDIR /app

# Copia el proxy y el build
COPY gradle.properties build.gradle ./
COPY gradle ./gradle

# Descarga dependencias usando el proxy
RUN gradle dependencies --no-daemon --refresh-dependencies --info

# Continúa con el build
COPY src ./src
RUN gradle build --no-daemon -x test

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]