package template.template.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import template.template.network.requests.CreateSampleEntityRequest;
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


    @PostMapping
    public ResponseEntity<?> createEntity(@RequestBody CreateSampleEntityRequest request) {
        sampleService.example(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/game/generate")
    public ResponseEntity<Void> generateLetter() {
        sampleGameState.generateLetter();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/game/move")
    public ResponseEntity<?> move() {
        sampleGameState.currentPlayerMoved();
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/join")
    public void join(@Payload String username, SimpMessageHeaderAccessor headerAccessor) {

        if(headerAccessor.getSessionAttributes().containsValue(username)){
            return;
        }
        headerAccessor.getSessionAttributes().put("username", username);
        System.out.println("Sesiunea " + headerAccessor.getSessionId() + " a fost marcată cu " + username);
    }



}
