FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY . .

RUN chmod +x ./gradlew && ./gradlew :eureka-server:build -x test

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /build/eureka-server/build/libs/*.jar app.jar

EXPOSE 8761

ENTRYPOINT ["java", "-jar", "app.jar"]
