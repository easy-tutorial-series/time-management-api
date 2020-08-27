package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@Slf4j
public class UserPostHandler implements Handler<RoutingContext> {
  private final MongoCollection<Document> userDocument;

  public UserPostHandler(MongoCollection<Document> userDocument) {
    this.userDocument = userDocument;
  }

  @Override
  public void handle(RoutingContext event) {
    JsonObject bodyAsJson = event.getBodyAsJson();
    String username = bodyAsJson.getString("username");
    String password = bodyAsJson.getString("password");
    Document document = new Document("username", username).append("password", password);
    HttpServerResponse response = event.response();
    response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    Single.fromPublisher(userDocument.insertOne(document))
      .subscribe(v -> {
        response.end(new JsonObject().put("username", username).encode());
      }, e -> {
        log.error("insert error", e);
        response.setStatusCode(400).end(new JsonObject().put("error", e.getMessage()).encode());
      });
  }
}
