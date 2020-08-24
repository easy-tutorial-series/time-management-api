package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonFailureHandler implements Handler<RoutingContext> {
  private static final Logger logger = LoggerFactory.getLogger(CommonFailureHandler.class);

  @Override
  public void handle(RoutingContext context) {
    logger.error("exception", context.failure());
    HttpServerResponse response = context.response();
    response.putHeader("content-type", "application/json");
    String error = new JsonObject().put("error", context.failure().getMessage()).encode();
    response.setStatusCode(500).end(error);
  }
}
