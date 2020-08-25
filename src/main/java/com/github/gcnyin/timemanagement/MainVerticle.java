package com.github.gcnyin.timemanagement;

import io.jsonwebtoken.security.Keys;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  @Override
  public void start(Promise<Void> startPromise) {
    vertx.createHttpServer()
      .requestHandler(bootstrap())
      .listen(8080, http -> {
        if (http.succeeded()) {
          startPromise.complete();
          log.info("HTTP Server started on port 8080");
        } else {
          log.error("HTTP Server stop", http.cause());
          startPromise.fail(http.cause());
        }
      });
  }

  private Router bootstrap() {
    String connectionString = "mongodb://root:password@localhost";
    String database = "test";
    MongoDbClient mongoDbClient = new MongoDbClient(connectionString, database);
    String secretString = "a8jh0Vf5C0lPAuTJO2vQYBJGT1ScVdeLI12C2gXDHo8=";
    SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes());
    JwtUtils jwtUtils = new JwtUtils(key);
    TokenPostHandler tokenPostHandler = new TokenPostHandler(jwtUtils, mongoDbClient);
    UserGetHandler userGetHandler = new UserGetHandler();
    CommonFailureHandler commonFailureHandler = new CommonFailureHandler();
    JwtAuthenticationHandler jwtAuthenticationHandler = new JwtAuthenticationHandler(jwtUtils);

    Router router = Router.router(vertx);
    router.post("/token")
      .handler(BodyHandler.create())
      .handler(tokenPostHandler)
      .failureHandler(commonFailureHandler);
    router.get("/user")
      .handler(jwtAuthenticationHandler)
      .handler(userGetHandler)
      .failureHandler(commonFailureHandler);
    return router;
  }
}
