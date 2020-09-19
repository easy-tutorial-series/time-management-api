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
    WebSocketHandler webSocketHandler = new WebSocketHandler();

    MainVerticle verticle = new MainVerticle(database, webSocketHandler);
    Vertx vertx = Vertx.vertx();
    Future<String> future = vertx.deployVerticle(verticle);
    future.onFailure(throwable -> {
      log.error("startup failed", throwable);
      vertx.close();
    }).onSuccess(s -> {
      long end = System.currentTimeMillis();
      log.info("deployId {}", s);
      long time = end - start;
      log.info("spent time {} millis", time);
    });
  }
}
