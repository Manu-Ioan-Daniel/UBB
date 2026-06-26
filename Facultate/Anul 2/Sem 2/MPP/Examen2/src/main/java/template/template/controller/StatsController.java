package template.template.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import template.template.domain.StatisticiJoc;
import template.template.service.GameStatsService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/stats")
@RequiredArgsConstructor
@CrossOrigin
public class StatsController {

    private final GameStatsService gameStatsService;

    @GetMapping("/{playerName}")
    public ResponseEntity<List<StatisticiJoc>> getPlayerHistory(@PathVariable String playerName) {

        List<StatisticiJoc> toateJocurile = gameStatsService.getStatisticiByPlayer(playerName);
        List<StatisticiJoc> jocuriFiltrate = new ArrayList<>();
        for (StatisticiJoc joc : toateJocurile) {
            int totalPuncteJoc = adunaPuncteleDinString(joc.getPunctaje());

            if (totalPuncteJoc >= 5) {
                jocuriFiltrate.add(joc);
            }
        }

        return ResponseEntity.ok(jocuriFiltrate);
    }

    /**
     * Metodă utilitară pentru a extrage și aduna punctajele din formatul text salvat:
     * Ex: "Scorul din Runda 1 : 3Scorul din Runda 2 : 4" -> Extrage 3 și 4 -> Total 7
     */
    private int adunaPuncteleDinString(String punctajeText) {
        if (punctajeText == null || punctajeText.isEmpty()) {
            return 0;
        }

        int sum = 0;
        Pattern pattern = Pattern.compile(":\\s*(-?\\d+)");
        Matcher matcher = pattern.matcher(punctajeText);

        while (matcher.find()) {
            try {
                sum += Integer.parseInt(matcher.group(1).trim());
            } catch (NumberFormatException e) {
            }
        }
        return sum;
    }
}