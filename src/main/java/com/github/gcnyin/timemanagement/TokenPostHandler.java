package com.github.gcnyin.timemanagement;

import com.github.gcnyin.timemanagement.exception.BadRequestException;
import com.mongodb.BasicDBObject;
import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
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
  private final MongoDatabase mongoDbClient;

  public TokenPostHandler(JwtUtils jwtUtils, MongoDatabase mongoDbClient) {
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
    MongoCollection<Document> collection = mongoDbClient.getCollection("user");
    BasicDBObject condition = new BasicDBObject().append("username", username);
    Publisher<Document> user = collection.find(condition).first();
    Single<Document> single = Single.fromPublisher(user);

    single
      .map(document -> {
        String p = document.getString("password");
        boolean equals = Objects.equals(p, password);
        if (!equals) {
          throw new BadRequestException("invalid username or password");
        }
        String id = document.getString("id");
        String token = this.jwtUtils.generate(id);
        return new JsonObject().put("token", token).encode();
      })
      .subscribe(
        response::end,
        e -> response.setStatusCode(500).end(new JsonObject().put("error", e.getMessage()).encode()));
  }
}
