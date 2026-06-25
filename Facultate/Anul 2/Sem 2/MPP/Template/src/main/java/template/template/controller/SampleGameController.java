package template.template.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import template.template.service.SampleGameState;
import template.template.service.SampleService;
import template.template.utils.Observer.Observer;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")
public class SampleGameController implements Observer {
    
    private final SampleService sampleService;
    private final SampleGameState sampleGameState;
    private final SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void init() {
        sampleGameState.addObserver(this);
    }

    @Override
    public void doUpdate() {
        messagingTemplate.convertAndSend("/api/state", sampleGameState);
    }

    @GetMapping("/game/state")
    @ResponseBody
    public ResponseEntity<?> getGameState() {
        return ResponseEntity.ok(sampleGameState);
    }


    @MessageMapping("/join")
    public void join(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", username);
        System.out.println("Sesiunea " + headerAccessor.getSessionId() + " a fost marcată cu " + username);
    }



}
