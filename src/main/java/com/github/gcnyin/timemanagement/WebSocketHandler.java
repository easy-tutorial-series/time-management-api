package com.github.gcnyin.timemanagement;

import io.vertx.core.Handler;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.impl.ConcurrentHashSet;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public class WebSocketHandler implements Handler<ServerWebSocket> {
  private final Set<ServerWebSocket> socketSet = new ConcurrentHashSet<>();

  @Override
  public void handle(ServerWebSocket webSocket) {
    socketSet.add(webSocket);
    webSocket.textMessageHandler(s -> {
      log.info("client send {}", s);
      socketSet.forEach(ws -> ws.writeTextMessage(s));
    });
    webSocket.closeHandler(v -> {
      log.info("websocket closed {}", webSocket.textHandlerID());
      socketSet.remove(webSocket);
    });
  }
}
