package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.get("/hello").handler(this::handler);
    vertx.createHttpServer()
      .requestHandler(router)
      .listen(8888);
  }

  private void handler(RoutingContext routingContext) {
    // This handler will be called for every request
    HttpServerResponse response = routingContext.response();
    response.putHeader("content-type", "text/plain");
    // Write to the response and end it
    response.end("Hello World from Vert.x-Web!");
  }
}
