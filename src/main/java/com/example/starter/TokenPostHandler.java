package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

@Slf4j
public class TokenPostHandler implements Handler<RoutingContext> {

  private final JwtUtils jwtUtils;

  public TokenPostHandler(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    var body = routingContext.getBodyAsJson();
    var response = routingContext.response();
    response.putHeader("content-type", "application/json");

    var username = body.getString("username");
    var password = body.getString("password");
    var isValid = validate(username, password);
    if (!isValid) {
      var message = new JsonObject().put("message", "invalid username or password").encode();
      response.setStatusCode(401).end(message);
      return;
    }
    var token = jwtUtils.generate(username);
    log.info("generated token");

    response.end(new JsonObject().put("token", token).encode());
  }

  private boolean validate(String username, String password) {
    return Objects.equals(username, password);
  }
}
