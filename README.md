# time-management-api

![https://vertx.io](https://img.shields.io/badge/vert.x-4.0.0.Beta1-purple.svg)

## Requirements

- Java 11
- MongoDB(using `docker-compose.yml`)

## Building

To run your application by Gradle:

```
./gradlew run
```

To package your application:

```
./gradlew clean shadowJar
```

To run your application as jar:

```
java -jar build/libs/time-management-api-all.jar
```
