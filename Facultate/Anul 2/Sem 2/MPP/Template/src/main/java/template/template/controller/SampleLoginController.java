package template.template.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import template.template.domain.Player;
import template.template.network.requests.LoginRequest;
import template.template.service.ConfigService;
import template.template.service.SampleGameState;
import template.template.service.SampleService;

import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class SampleLoginController {

    private final SampleService sampleService;
    private final SampleGameState sampleGameState;


    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody LoginRequest loginRequest) {

        Optional<Player> sampleEntity = sampleService.findOne(loginRequest.getUsername());

        if (sampleEntity.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }

        if(sampleGameState.playerExists(sampleEntity.get())) {
            return ResponseEntity.badRequest().body("Someone already logged in bro");
        }

        sampleGameState.addPlayer(sampleEntity.get());
        return ResponseEntity.ok().build();

    }


}
