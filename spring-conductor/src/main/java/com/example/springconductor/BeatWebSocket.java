package com.example.springconductor;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BeatWebSocket extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    public void broadcast(String s) {
        for (WebSocketSession webSocketSession : sessions.values()) {
            try {
                webSocketSession.sendMessage(new TextMessage(s));
            } catch (IOException e) {
                sessions.remove(webSocketSession.getId());
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }
}
