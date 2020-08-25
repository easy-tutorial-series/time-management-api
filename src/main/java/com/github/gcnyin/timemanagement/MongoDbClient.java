package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;
import lombok.Getter;

@Getter
public class MongoDbClient {
  private final MongoDatabase database;

  /**
   * @param connectionString see https://docs.mongodb.com/manual/reference/connection-string/
   * @param database MongoDB database name
   */
  public MongoDbClient(String connectionString, String database) {
    this.database = MongoClients.create(connectionString).getDatabase(database);
  }
}
