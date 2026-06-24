package org.example.controller;

import org.example.handler.GameWebSocketHandler;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ExampleRestController {

    // Injectat dacă vrei să combini REST cu broadcast WebSocket
    private final GameWebSocketHandler wsHandler;

    public ExampleRestController(GameWebSocketHandler wsHandler) {
        this.wsHandler = wsHandler;
    }

    // ─── Exemplu GET ──────────────────────────────────────────────────────────────

    /**
     * GET /api/status
     * Returnează câți jucători sunt conectați.
     * Adaptează să returneze ce ai nevoie din cerință.
     */
    @GetMapping("/status")
    public String getStatus() {
        return "Jucători conectați: " + wsHandler.getJucatoriConectati();
    }

    // ─── Exemplu POST ─────────────────────────────────────────────────────────────

    /**
     * POST /api/actiune
     * Primește un JSON body și procesează.
     *
     * Exemplu request body:
     * { "cheie": "valoare" }
     */
    @PostMapping("/actiune")
    public String postActiune(@RequestBody java.util.Map<String, String> body) {
        // Procesezi body-ul și returnezi un răspuns
        // Poți și să faci broadcast prin wsHandler dacă e nevoie
        return "Primit: " + body.toString();
    }
}
