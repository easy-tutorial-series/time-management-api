# time-management-api

![https://vertx.io](https://img.shields.io/badge/vert.x-4.0.0.Beta1-purple.svg)
![https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://img.shields.io/badge/Java-11-yellow.svg)

## Pattern

Only one verticle with routers.

## Requirements

- [Java 11](./docs/install-java-11.md)
- MongoDB(using `docker-compose up -d`)

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
