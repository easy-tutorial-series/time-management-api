package com.github.gcnyin.timemanagement;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class UserGetHandler implements Handler<RoutingContext> {
  @Override
  public void handle(RoutingContext ctx) {
    HttpServerResponse response = ctx.response();
    String userId = ctx.get("userId");
    response.putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    response.end(new JsonObject().put("userId", userId).encode());
  }
}
