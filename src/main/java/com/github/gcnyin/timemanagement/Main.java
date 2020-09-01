package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    String mongoUser = System.getenv("MONGO_USER");
    String mongoPassword = System.getenv("MONGO_PASSWORD");
    String mongoHost = System.getenv("MONGO_HOST");
    String databaseName = System.getenv("MONGO_DATABASE");
    String connectionString = "mongodb://" + mongoUser + ":" + mongoPassword + "@" + mongoHost;
    MongoDatabase database = MongoClients.create(connectionString).getDatabase(databaseName);

    MainVerticle verticle = new MainVerticle(database);
    Vertx vertx = Vertx.vertx();
    Future<String> future = vertx.deployVerticle(verticle);
    future.onComplete(result -> {
      if (result.failed()) {
        log.error("startup failed", result.cause());
        vertx.close();
      } else {
        long end = System.currentTimeMillis();
        log.info("deployId {}", result.result());
        long time = end - start;
        log.info("spent time {} millis", time);
      }
    });
  }
}
