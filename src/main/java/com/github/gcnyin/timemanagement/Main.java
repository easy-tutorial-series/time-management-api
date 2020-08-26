package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.vertx.core.Vertx;

public class Main {
  public static void main(String[] args) {
    String connectionString = "mongodb://root:password@localhost";
    String databaseName = "test";
    MongoDatabase database = MongoClients.create(connectionString).getDatabase(databaseName);

    MainVerticle verticle = new MainVerticle(database);
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(verticle);
  }
}
