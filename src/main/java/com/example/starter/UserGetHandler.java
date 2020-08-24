package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetHandler implements Handler<RoutingContext> {
  private final JwtUtils jwtUtils;

  public UserGetHandler(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    HttpServerResponse response = routingContext.response();
    String token = routingContext.request().getHeader("Authorization");
    String username = jwtUtils.parseBody(token).getSubject();

    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("username", username).encode());
  }
}
