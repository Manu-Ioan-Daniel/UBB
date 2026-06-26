package template.template.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import template.template.service.JocGameState;
import template.template.service.JocService;
import template.template.utils.Observer.Observer;

import java.util.Map;

@Controller
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api")
public class JocGameController implements Observer {

    private static final Logger logger = LogManager.getLogger(JocGameController.class);

    private final JocService jocService;
    private final JocGameState jocGameState;
    private final SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    public void init() {
        jocGameState.addObserver(this);
    }

    @Override
    public void doUpdate() {
        logger.info("doUpdate: Broadcast stare joc la /api/state");
        messagingTemplate.convertAndSend("/api/state", jocGameState);
    }

    @GetMapping("/game/state")
    @ResponseBody
    public ResponseEntity<?> getGameState() {
        return ResponseEntity.ok(jocGameState);
    }

    @MessageMapping("/join")
    public void join(@Payload String porecla, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", porecla);
        logger.info("Sesiune WS " + headerAccessor.getSessionId() + " asociata cu " + porecla);
        if (!jocGameState.esteJucatorInregistratInJoc(porecla)) {
            jocService.gasesteJucator(porecla).ifPresent(jucator -> {
                jocService.intrareJoc(jucator, jucator.getVarsta());
            });
        }
    }

    @PostMapping("/game/choose-letter")
    @ResponseBody
    public ResponseEntity<?> chooseLetter(@RequestParam String porecla) {
        try {
            jocService.alegeLitera(porecla);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Eroare la alegere litera: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/game/submit-answers")
    @ResponseBody
    public ResponseEntity<?> submitAnswers(@RequestParam String porecla, @RequestBody Map<String, String> raspunsuri) {
        try {
            jocService.trimiteRaspunsuri(porecla, raspunsuri);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            logger.error("Eroare la trimitere raspunsuri: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
