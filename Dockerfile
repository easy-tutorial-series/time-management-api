FROM gradle:6.6.1-jdk14 AS builder
WORKDIR /builder
COPY . /builder
RUN ls -l
RUN gradle build

FROM openjdk:14-jdk
WORKDIR /app
COPY --from=builder /builder/build/libs/time-management-api-all.jar /app/api.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "api.jar"]
