package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServerRequest;

public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(this::getEnd)
      .listen(8888, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          System.out.println("HTTP server started on port 8888");
        } else {
          startPromise.fail(http.cause());
        }
      });
  }

  private Future<Void> getEnd(HttpServerRequest req) {
    return req.response()
      .putHeader("content-type", "application/json")
      .end("{\"status\": \"success\"}");
  }
}
