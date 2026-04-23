FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x ./gradlew && ./gradlew :recommendation-service:build -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/recommendation-service/build/libs/*.jar app.jar

EXPOSE 8083

ENTRYPOINT ["java", "-jar", "app.jar"]
