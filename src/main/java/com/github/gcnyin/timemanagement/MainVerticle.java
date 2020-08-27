package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoCollection;
import com.mongodb.reactivestreams.client.MongoDatabase;
import io.jsonwebtoken.security.Keys;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import javax.crypto.SecretKey;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  private final MongoDatabase mongoDatabase;

  public MainVerticle(MongoDatabase mongoDatabase) {
    this.mongoDatabase = mongoDatabase;
  }

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
    Single.fromPublisher(mongoDatabase.listCollectionNames())
      .timeout(5, TimeUnit.SECONDS)
      .subscribe(log::info, e -> {
        log.error("mongo connection error", e);
        vertx.close();
      });
    String secretString = "a8jh0Vf5C0lPAuTJO2vQYBJGT1ScVdeLI12C2gXDHo8=";
    SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes());
    JwtUtils jwtUtils = new JwtUtils(key);
    CommonFailureHandler commonFailureHandler = new CommonFailureHandler();
    JwtAuthenticationHandler jwtAuthenticationHandler = new JwtAuthenticationHandler(jwtUtils);
    TokenPostHandler tokenPostHandler = new TokenPostHandler(jwtUtils, mongoDatabase);
    UserGetHandler userGetHandler = new UserGetHandler();
    UserPostHandler userPostHandler = new UserPostHandler(mongoDatabase.getCollection("user"));

    Router router = Router.router(vertx);
    router.post("/token")
      .handler(BodyHandler.create())
      .handler(tokenPostHandler)
      .failureHandler(commonFailureHandler);
    router.get("/user")
      .handler(jwtAuthenticationHandler)
      .handler(userGetHandler)
      .failureHandler(commonFailureHandler);
    router.post("/user")
      .handler(BodyHandler.create())
      .handler(userPostHandler)
      .failureHandler(commonFailureHandler);
    return router;
  }

  @Override
  public void stop() {
    log.info("MainVerticle stopped");
  }
}
