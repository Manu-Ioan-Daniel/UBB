package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.models.Config;
import org.example.network.responses.ConfigResponse;
import org.example.services.ConfigService;
import org.example.services.GameState;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ConfigController {

    private final ConfigService configService;
    private final GameState gameState;

    @GetMapping("/config")
    public ResponseEntity<?> getConfigs(@RequestParam int n) {

        List<Config> listaConfigurari = configService.getConfigs(n);
        System.out.println("Got configs" + listaConfigurari);
        ConfigResponse response = new ConfigResponse();
        response.setConfigs(listaConfigurari);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/choose-config")
    public ResponseEntity<?> chooseConfig() {

    }

}
