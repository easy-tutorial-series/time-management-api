FROM openjdk:11-jdk-slim AS builder
WORKDIR /app
COPY . /app
RUN ./mvnw clean package

FROM openjdk:11-jre-slim
WORKDIR /app
COPY --from=builder /app/target/time-management-api-0.1-fat.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
