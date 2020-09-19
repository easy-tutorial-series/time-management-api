package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoDatabase;
import io.jsonwebtoken.security.Keys;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MainVerticle extends AbstractVerticle {
  private final MongoDatabase mongoDatabase;
  private final WebSocketHandler webSocketHandler;

  public MainVerticle(MongoDatabase mongoDatabase, WebSocketHandler webSocketHandler) {
    this.mongoDatabase = mongoDatabase;
    this.webSocketHandler = webSocketHandler;
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Single<Router> routerSingle = bootstrapRouter();
    routerSingle.subscribe(
      router -> boostrapVertx(startPromise, router),
      startPromise::fail);
  }

  private void boostrapVertx(Promise<Void> startPromise, Router router) {
    vertx.createHttpServer()
      .requestHandler(router)
      .webSocketHandler(webSocketHandler)
      .listen(8080, http -> handleHttpStartResult(startPromise, http));
  }

  private void handleHttpStartResult(Promise<Void> startPromise, AsyncResult<HttpServer> http) {
    if (http.succeeded()) {
      startPromise.complete();
      log.info("HTTP Server started on port 8080");
    } else {
      log.error("HTTP Server startup failed", http.cause());
      startPromise.fail(http.cause());
    }
  }

  private Single<Router> bootstrapRouter() {
    return Flowable.fromPublisher(mongoDatabase.listCollectionNames())
      .timeout(5, TimeUnit.SECONDS)
      .count()
      .map(i -> {
        log.info("mongodb health check passed");
        return getRouter();
      });
  }

  private Router getRouter() {
    String secretString = "a8jh0Vf5C0lPAuTJO2vQYBJGT1ScVdeLI12C2gXDHo8=";
    SecretKey key = Keys.hmacShaKeyFor(secretString.getBytes());
    JwtUtils jwtUtils = new JwtUtils(key);
    CommonFailureHandler commonFailureHandler = new CommonFailureHandler();
    JwtAuthenticationHandler jwtAuthenticationHandler = new JwtAuthenticationHandler(jwtUtils);
    BodyHandler bodyHandler = BodyHandler.create();
    TokenPostHandler tokenPostHandler = new TokenPostHandler(jwtUtils, mongoDatabase);
    UserGetHandler userGetHandler = new UserGetHandler();
    UserPostHandler userPostHandler = new UserPostHandler(mongoDatabase.getCollection("user"));
    CardPostHandler cardPostHandler = new CardPostHandler(mongoDatabase.getCollection("card"));

    Router router = Router.router(vertx);
    router.post("/token")
      .handler(bodyHandler)
      .handler(tokenPostHandler)
      .failureHandler(commonFailureHandler);
    router.get("/user")
      .handler(jwtAuthenticationHandler)
      .handler(userGetHandler)
      .failureHandler(commonFailureHandler);
    router.post("/user")
      .handler(bodyHandler)
      .handler(userPostHandler)
      .failureHandler(commonFailureHandler);
    router.post("/card")
      .handler(jwtAuthenticationHandler)
      .handler(bodyHandler)
      .handler(cardPostHandler)
      .failureHandler(commonFailureHandler);
    return router;
  }

  @Override
  public void stop() {
    log.info("MainVerticle stopped");
  }
}
