package com.example.starter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  private final String secretString = "a8jh0Vf5C0lPAuTJO2vQYBJGT1ScVdeLI12C2gXDHo8=";
  private final Key key = Keys.hmacShaKeyFor(secretString.getBytes());

  @Override
  public void start(Promise<Void> startPromise) {
    var router = Router.router(vertx);
    router.post("/token").handler(this::tokenHandler);
    router.get("/other").handler(this::otherHandler);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8080, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          logger.info("HTTP Server started on port 8080");
        } else {
          logger.error("HTTP Server stop", http.cause());
          startPromise.fail(http.cause());
        }
      });
  }

  private void tokenHandler(RoutingContext routingContext) {
    Date issuedAt = new Date();
    Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.DAYS));
    var token = Jwts.builder()
      .signWith(key)
      .setIssuer("me")
      .setSubject("Bob")
      .setAudience("you")
      .setIssuedAt(issuedAt)
      .setExpiration(expiration)
      .setId(String.valueOf(UUID.randomUUID()))
      .compact();
    logger.info("generated token");

    var response = routingContext.response();
    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("token", token).encode());
  }

  private void otherHandler(RoutingContext routingContext) {
    var response = routingContext.response();
    try {
      String token = routingContext.request().getHeader("Authorization");
      Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
      logger.info("jws: {}", jws);

      response.putHeader("content-type", "application/json");
      response.end(new JsonObject().put("status", "ok").encode());
    } catch (Exception e) {
      logger.error("exception", e);
      response.setStatusCode(500).end();
    }
  }
}
