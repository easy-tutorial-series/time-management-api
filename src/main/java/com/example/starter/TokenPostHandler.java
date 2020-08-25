package com.example.starter;

import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
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
  private final MongoDbClient mongoDbClient;

  public TokenPostHandler(JwtUtils jwtUtils, MongoDbClient mongoDbClient) {
    this.jwtUtils = jwtUtils;
    this.mongoDbClient = mongoDbClient;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    JsonObject body = routingContext.getBodyAsJson();
    HttpServerResponse response = routingContext.response();
    response.putHeader("content-type", "application/json");

    String username = body.getString("username");
    String password = body.getString("password");
    MongoCollection<Document> collection = mongoDbClient.getDatabase().getCollection("user");
    BasicDBObject condition = new BasicDBObject().append("username", username);
    Publisher<Document> user = collection.find(condition).first();
    Single<Document> single = Single.fromPublisher(user);

    single.subscribe(u -> {
      String p = u.getString("password");
      boolean equals = Objects.equals(p, password);
      if (!equals) {
        String message = new JsonObject().put("message", "invalid username or password").encode();
        response.setStatusCode(401).end(message);
      } else {
        String id = u.getString("id");
        String token = this.jwtUtils.generate(id);
        response.end(new JsonObject().put("token", token).encode());
      }
    }, e -> response.end(new JsonObject().put("error", e.getMessage()).encode()));
  }

}
