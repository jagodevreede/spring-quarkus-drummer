package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

@ServerEndpoint("/beat/{name}")
@ApplicationScoped
public class BeatWebSocket {
    private final static Map<String, Session> sessions = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, @PathParam("name") String name) {
        sessions.put(name, session);
    }
    @OnClose
    public void onClose(Session session, @PathParam("name") String name) {
        sessions.remove(name);
    }
    @OnError
    public void onError(Session session, @PathParam("name") String name, Throwable throwable) {
        sessions.remove(name);
    }
    public void broadcast(String message) {
        sessions.values().forEach(s -> {
            s.getAsyncRemote().sendObject(message, result ->  {
                if (result.getException() != null) {
                    System.out.println("Unable to send message: " + result.getException());
                }
            });
        });
    }
}
