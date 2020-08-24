package com.example.starter;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpHeaders;
import io.vertx.ext.web.RoutingContext;

public class JwtAuthenticationHandler implements Handler<RoutingContext> {
  private final JwtUtils jwtUtils;

  public JwtAuthenticationHandler(JwtUtils jwtUtils) {
    this.jwtUtils = jwtUtils;
  }

  @Override
  public void handle(RoutingContext ctx) {
    String token = ctx.request().getHeader(HttpHeaders.AUTHORIZATION);
    String username = jwtUtils.parseBody(token).getSubject();
    ctx.put("username", username);
    ctx.next();
  }
}
