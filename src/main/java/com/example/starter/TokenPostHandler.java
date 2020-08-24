package com.example.starter;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.reactivestreams.Publisher;

import java.util.Objects;

@Slf4j
public class TokenPostHandler implements Handler<RoutingContext> {

  private final JwtUtils jwtUtils;
  private final DbClient dbClient;

  public TokenPostHandler(JwtUtils jwtUtils, DbClient dbClient) {
    this.jwtUtils = jwtUtils;
    this.dbClient = dbClient;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    JsonObject body = routingContext.getBodyAsJson();
    HttpServerResponse response = routingContext.response();
    response.putHeader("content-type", "application/json");

    String username = body.getString("username");
    String password = body.getString("password");
    Single<Boolean> isValidFuture = validate(username, password);

    isValidFuture.subscribe(isValid -> {
      if (!isValid) {
        String message = new JsonObject().put("message", "invalid username or password").encode();
        response.setStatusCode(401).end(message);
        return;
      }
      String token = jwtUtils.generate(username);
      response.end(new JsonObject().put("token", token).encode());
    });
  }

  private Single<Boolean> validate(String username, String password) {
    MongoCollection<Document> collection = dbClient.getDatabase().getCollection("user");
    BasicDBObject condition = new BasicDBObject().append("username", username);
    Publisher<Document> user = collection.find(condition).first();
    return Single.fromPublisher(user)
      .map(document -> Objects.equals(password, document.getString("password")));
  }
}
