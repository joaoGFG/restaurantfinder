FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x ./gradlew && ./gradlew :user-service:build -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/user-service/build/libs/*.jar app.jar

EXPOSE 8082

ENTRYPOINT ["java", "-jar", "app.jar"]
