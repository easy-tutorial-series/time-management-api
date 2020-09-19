package com.github.gcnyin.timemanagement;

import com.mongodb.reactivestreams.client.MongoCollection;
import io.reactivex.rxjava3.core.Single;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.UUID;

@Slf4j
public class CardPostHandler implements Handler<RoutingContext> {
  private final MongoCollection<Document> cardDocument;

  public CardPostHandler(MongoCollection<Document> cardDocument) {
    this.cardDocument = cardDocument;
  }

  @Override
  public void handle(RoutingContext ctx) {
    JsonObject body = ctx.getBodyAsJson();
    String cardContent = body.getString("cardContent");
    String cardType = body.getString("cardType");
    String cardId = UUID.randomUUID().toString();
    String userId = ctx.get("userId");
    Document newCard = new Document("id", cardId)
      .append("userId", userId)
      .append("cardContent", cardContent)
      .append("cardType", cardType);
    HttpServerResponse response = ctx.response();
    response.putHeader("content-type", "application/json");
    Single.fromPublisher(cardDocument.insertOne(newCard))
      .doOnError(throwable -> {
        log.error("can not insert new card", throwable);
        response.setStatusCode(500).end(new JsonObject().put("error", throwable.getMessage()).encode());
      })
      .doOnSuccess(r -> {
        log.info("insert new card, cardId: {}", cardId);
        response.end(new JsonObject().put("cardId", cardId).encode());
      })
      .subscribe();
  }
}
