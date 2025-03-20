package com.swd.pregnancycare.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class MyWebSocketHandler extends TextWebSocketHandler {
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    // Echo lại thông điệp nhận được
    session.sendMessage(new TextMessage("Echo: " + message.getPayload()));
  }
}
