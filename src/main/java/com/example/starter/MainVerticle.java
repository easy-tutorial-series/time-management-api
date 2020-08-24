package com.example.starter;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  private final CommonFailureHandler commonFailureHandler = new CommonFailureHandler();

  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(bootstrap())
      .listen(8080, http -> httpCallback(http, startPromise));
  }

  private void httpCallback(AsyncResult<HttpServer> http, Promise<Void> startPromise) {
    if (http.succeeded()) {
      startPromise.complete();
      log.info("HTTP Server started on port 8080");
    } else {
      log.error("HTTP Server stop", http.cause());
      startPromise.fail(http.cause());
    }
  }

  private Router bootstrap() {
    String connectionString = "mongodb://root:password@localhost";
    DbClient dbClient = new DbClient(connectionString, "test");
    JwtUtils jwtUtils = new JwtUtils();
    TokenPostHandler tokenPostHandler = new TokenPostHandler(jwtUtils, dbClient);
    UserGetHandler userGetHandler = new UserGetHandler(jwtUtils, dbClient);

    var router = Router.router(vertx);
    router.post("/token")
      .handler(BodyHandler.create())
      .handler(tokenPostHandler)
      .failureHandler(commonFailureHandler);
    router.get("/user")
      .handler(userGetHandler)
      .failureHandler(commonFailureHandler);
    return router;
  }
}
