# time-management-api

![https://vertx.io](https://img.shields.io/badge/vert.x-4.0.0.Beta1-purple.svg)
![https://www.oracle.com/java/technologies/javase-jdk11-downloads.html](https://img.shields.io/badge/Java-11-yellow.svg)

## Requirements

- Java 14
- MongoDB

## Build

```
./gradlew clean build
```

## Run

Running the application only needs one step.

```
$ docker-compose up -d
```

Or you can build the jar file, and manually run it.

```
$ ./gradlew clean build
$ java -jar build/libs/time-management-api-all.jar
```
