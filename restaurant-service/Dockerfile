FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x ./gradlew && ./gradlew :restaurant-service:build -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/restaurant-service/build/libs/*.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "app.jar"]
