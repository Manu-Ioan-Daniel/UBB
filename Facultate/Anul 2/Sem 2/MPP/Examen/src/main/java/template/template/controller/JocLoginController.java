package template.template.controller;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import template.template.domain.Jucator;
import template.template.network.requests.LoginRequest;
import template.template.service.JocGameState;
import template.template.service.JocService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class JocLoginController {

    private static final Logger logger = LogManager.getLogger(JocLoginController.class);

    private final JocService jocService;
    private final JocGameState jocGameState;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest loginRequest) {
        logger.info("Request login: porecla={}", loginRequest.getPorecla());
        Optional<Jucator> jucatorOpt = jocService.gasesteJucator(loginRequest.getPorecla());

        if (jucatorOpt.isEmpty()) {
            logger.warn("Jucator neinregistrat: {}", loginRequest.getPorecla());
            return ResponseEntity.badRequest().body("Porecla nu corespunde unui jucator inregistrat!");
        }

        Jucator jucator = jucatorOpt.get();
        if (jocGameState.esteJucatorInregistratInJoc(jucator.getPorecla())) {
            logger.warn("Jucator deja in joc: {}", jucator.getPorecla());
            return ResponseEntity.badRequest().body("Jucatorul este deja intrat in joc!");
        }

        jocService.intrareJoc(jucator, loginRequest.getVarsta());
        return ResponseEntity.ok(jucator);
    }
}
