package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
  public static void main(String[] args) {
    String connectionString = "mongodb://root:password@localhost";
    String databaseName = "test";
    MongoDatabase database = MongoClients.create(connectionString).getDatabase(databaseName);

    MainVerticle verticle = new MainVerticle(database);
    Vertx vertx = Vertx.vertx();
    Future<String> future = vertx.deployVerticle(verticle);
    future.onComplete(s -> {
      if (s.failed()) {
        log.error("failed");
        vertx.close();
      } else {
        String result = s.result();
        log.info("deployId {}", result);
      }
    });
  }
}
