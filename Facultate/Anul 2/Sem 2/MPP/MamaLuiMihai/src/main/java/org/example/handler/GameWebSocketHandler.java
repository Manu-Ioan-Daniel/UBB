package org.example.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.example.network.Message;
import org.example.observer.Observer;
import org.example.services.GameState;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler implements Observer {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final GameState gameState;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @PostConstruct
    public void init() {
        gameState.addObserver(this);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        return;
    }

    public void broadcast(Message msg) {

        sessions.forEach((porecla, session) -> {
            if (session.isOpen()) {
                sendTo(session, msg);
            }
        });
    }


    private void sendTo(WebSocketSession session, Message msg) {
        try {
            session.sendMessage(new TextMessage(msg.toJson()));
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }


    private String getFieldBySession(WebSocketSession target) {
        return sessions.entrySet().stream()
            .filter(e -> e.getValue().getId().equals(target.getId()))
            .map(java.util.Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    @Override
    public void update(GameState gameState) {
        try{
            String state = objectMapper.writeValueAsString(gameState);
            broadcast(Message.response(state));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
