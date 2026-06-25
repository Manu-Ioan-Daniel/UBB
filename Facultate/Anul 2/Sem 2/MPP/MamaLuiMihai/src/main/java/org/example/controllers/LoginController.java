package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.network.requests.LoginRequest;
import org.example.services.GameState;
import org.example.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController{

    private final PlayerService playerService;
    private final GameState gameState;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest ){

        System.out.println("Login request received for porecla: " + loginRequest.getPorecla());
        if(playerService.existaPorecla(loginRequest.getPorecla())){
            boolean succes = gameState.addPlayer(loginRequest.getPorecla());
            return succes ? ResponseEntity.ok().build() : ResponseEntity.status(409).body("User deja conectat cu porecla asta");
        }
        return ResponseEntity.status(409).body("Nu exista porecla micule tung tung");

    }

}
