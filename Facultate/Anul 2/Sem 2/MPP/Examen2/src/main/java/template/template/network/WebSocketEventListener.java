package template.template.network;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import template.template.service.SampleGameState;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SampleGameState sampleGameState;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null){
            sampleGameState.removePlayer(username);
        }
    }
}
