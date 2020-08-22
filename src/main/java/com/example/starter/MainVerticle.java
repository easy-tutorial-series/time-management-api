package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class MainVerticle extends AbstractVerticle {
  private static final Logger logger = LoggerFactory.getLogger(MainVerticle.class);

  private final JwtUtils jwtUtils = new JwtUtils();

  @Override
  public void start(Promise<Void> startPromise) {
    var router = Router.router(vertx);
    router.post("/token")
      .handler(BodyHandler.create())
      .handler(this::tokenHandler)
      .failureHandler(this::commonFailureHandler);
    router.get("/user")
      .handler(this::otherHandler)
      .failureHandler(this::commonFailureHandler);
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

  private void commonFailureHandler(RoutingContext ctx) {
    logger.error("exception", ctx.failure());
    var response = ctx.response();
    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("error", ctx.failure().getMessage()).encode());
  }

  private void tokenHandler(RoutingContext routingContext) {
    var body = routingContext.getBodyAsJson();
    logger.info("body: {}", body);
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
    logger.info("generated token");

    response.end(new JsonObject().put("token", token).encode());
  }

  private void otherHandler(RoutingContext routingContext) {
    var response = routingContext.response();
    var token = routingContext.request().getHeader("Authorization");
    var jws = jwtUtils.parse(token);
    var username = jws.getBody().getSubject();

    response.putHeader("content-type", "application/json");
    response.end(new JsonObject().put("username", username).encode());
  }

  private boolean validate(String username, String password) {
    return Objects.equals(username, password);
  }
}
