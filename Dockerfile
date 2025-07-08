FROM gradle:8.7-jdk17-alpine as builder
WORKDIR /app
COPY build.gradle ./
COPY gradle ./gradle
RUN gradle dependencies --no-daemon
COPY src ./src
RUN gradle build --no-daemon -x test
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]