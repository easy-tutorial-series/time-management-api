package com.example.starter;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Flowable;
import io.vertx.core.Handler;
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
    var body = routingContext.getBodyAsJson();
    var response = routingContext.response();
    response.putHeader("content-type", "application/json");

    var username = body.getString("username");
    var password = body.getString("password");
    var isValidFuture = validate(username, password);

    isValidFuture.subscribe(isValid -> {
      if (!isValid) {
        var message = new JsonObject().put("message", "invalid username or password").encode();
        response.setStatusCode(401).end(message);
        return;
      }
      var token = jwtUtils.generate(username);
      response.end(new JsonObject().put("token", token).encode());
    });
  }

  private Flowable<Boolean> validate(String username, String password) {
    MongoCollection<Document> collection = dbClient.getDatabase().getCollection("user");
    BasicDBObject condition = new BasicDBObject().append("username", username);
    Publisher<Document> user = collection.find(condition).first();
    return Flowable.fromPublisher(user)
      .map(document -> Objects.equals(password, document.getString("password")));
  }
}
