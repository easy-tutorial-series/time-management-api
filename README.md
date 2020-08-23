# time-management-back-end

![https://vertx.io](https://img.shields.io/badge/vert.x-4.0.0.Beta1-purple.svg)

## Requirements

- Java 11
- MongoDB(using `docker-compose.yml`)

## Building

To run your application by Maven:

```
./mvnw compile exec:java
```

To package your application:

```
./mvnw clean package
```

The result is `target/time-management-api-0.1-fat.jar`.

To run your application as jar:

```
java -jar target/time-management-api-0.1-fat.jar
```
