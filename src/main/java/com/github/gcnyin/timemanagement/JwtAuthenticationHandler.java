package com.github.gcnyin.timemanagement;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtAuthenticationHandler implements Handler<RoutingContext> {
  private final JwtUtils jwtUtils;

  public JwtAuthenticationHandler(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public void handle(RoutingContext ctx) {
    String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION);
    try {
      String userId = jwtUtils.parseBody(token).getSubject();
      ctx.put("userId", userId);
    } catch (Exception e) {
      log.warn("jwt authentication failed");
      ctx.response().putHeader(HttpHeaders.CONTENT_TYPE, "application/json");
      String errorMessage = new JsonObject().put("error", "invalid JWT token").encode();
      ctx.response().setStatusCode(401).end(errorMessage);
      return;
    }
    ctx.next();
  }
}
