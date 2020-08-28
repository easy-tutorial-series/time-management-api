FROM gradle:6.6.1-jdk11 AS builder
WORKDIR /builder
COPY . /builder
RUN gradle build

FROM openjdk:11-jre-buster
WORKDIR /app
COPY --from=builder /builder/build/libs/time-management-api-all.jar /app/api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]
