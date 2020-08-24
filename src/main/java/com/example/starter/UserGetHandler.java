package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext ctx) {
    HttpServerResponse response = ctx.response();
    String username = ctx.get("username");
    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("username", username).encode());
  }
}
