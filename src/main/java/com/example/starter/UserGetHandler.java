package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetHandler implements Handler<RoutingContext> {
  private final JwtUtils jwtUtils;
  private final DbClient dbClient;

  public UserGetHandler(JwtUtils jwtUtils, DbClient dbClient) {
    this.jwtUtils = jwtUtils;
    this.dbClient = dbClient;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    var response = routingContext.response();
    var token = routingContext.request().getHeader("Authorization");
    var jws = jwtUtils.parse(token);
    var username = jws.getBody().getSubject();

    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("username", username).encode());
  }
}
