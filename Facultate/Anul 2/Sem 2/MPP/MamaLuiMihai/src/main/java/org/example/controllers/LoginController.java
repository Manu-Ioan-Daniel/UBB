package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.requests.LoginRequest;
import org.example.services.GameState;
import org.example.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class LoginController{

    private final PlayerService playerService;


    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest ){

        if(playerService.existaPorecla(loginRequest.getPorecla())){
            boolean succes = GameState.getInstance().addPlayer(loginRequest.getPorecla());
            return succes ? ResponseEntity.ok().build() : ResponseEntity.status(409).body("Porecla deja exista");
        }
        return ResponseEntity.status(409).body("Porecla deja exista");

    }

}
