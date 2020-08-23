package com.example.starter;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Getter;

@Getter
public class DbClient {
  private final MongoDatabase database;

  public DbClient(String connectionString, String database) {
    this.database = MongoClients.create(connectionString).getDatabase(database);
  }
}
