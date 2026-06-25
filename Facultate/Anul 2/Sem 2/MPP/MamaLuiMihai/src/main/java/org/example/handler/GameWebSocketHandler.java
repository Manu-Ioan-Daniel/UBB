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
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler implements Observer {

    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final GameState gameState;

    @PostConstruct
    public void init() {
        gameState.addObserver(this);
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        if(sessions.contains(session)) {
            System.out.println("Session already exists!");
            return;
        }
        sessions.add(session);

        String query = session.getUri().getQuery();
        String porecla = "Anonim";
        if (query != null && query.contains("porecla=")) {
            porecla = query.split("porecla=")[1].split("&")[0];
        }

        session.getAttributes().put("porecla", porecla);

        try {
            if (session.isOpen()) {
                String state = objectMapper.writeValueAsString(gameState);
                sendTo(session, Message.response(state));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("Connection established pentru: " + porecla);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        String porecla = (String) session.getAttributes().get("porecla");
        gameState.removePlayer(porecla);
    }

    public void broadcast(Message msg) {

        sessions.forEach((session) -> {
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

    @Override
    public void update() {
        try{
            String state = objectMapper.writeValueAsString(gameState);
            System.out.println(state);
            broadcast(Message.response(state));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
