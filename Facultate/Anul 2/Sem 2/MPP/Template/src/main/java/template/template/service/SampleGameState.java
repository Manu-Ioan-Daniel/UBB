package template.template.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import template.template.domain.Config;
import template.template.domain.Player;
import template.template.domain.StatisticiJoc;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Getter
@Setter
@NoArgsConstructor
public class SampleGameState extends Observable {

    @Autowired
    private ConfigService configService;
    @Autowired
    private GameStatsService  gameStatsService;
    private final ConcurrentHashMap<String, Integer> positions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> scores = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, Integer>> roundScores = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ConcurrentHashMap<Integer, List<Integer>>> roundPositions = new ConcurrentHashMap<>();

    private final List<String> players = new CopyOnWriteArrayList<>();
    private List<String> winners = new java.util.ArrayList<>();
    private int highestTotalScore = 0;
    private int nrOfPlayers = 0;
    private int playerTurn = 0;
    private final int n = 2;
    private String state;
    private String chosenOne;
    private List<Config> configs;
    private Config currentConfig;
    private int currentRound = 1;
    private String lastActionMessage;

    public void addPlayer(Player player) {
        if (!players.contains(player.getName())) {
            players.add(player.getName());
            nrOfPlayers++;
            if(nrOfPlayers == n){
                this.state = "ready";
                chosenOne = players.get(n / 2 - 1);
                configs = configService.getConfigs(n);
            } else {
                this.state = "not_ready";
            }
            notifyObservers();
        }
    }

    public boolean playerExists(Player player) {
        return players.contains(player.getName());
    }

    public void removePlayer(String username){
        if(!players.contains(username)){
            return;
        }
        players.remove(username);
        nrOfPlayers--;
        notifyObservers();
    }

    public void setCurrentConfig(Config config) {
        this.currentConfig = config;
        this.state = "started";

        for (String p : players) {
            positions.put(p, 0);
            scores.put(p, 0);

            roundScores.put(p, new ConcurrentHashMap<>());

            ConcurrentHashMap<Integer, List<Integer>> posPerRound = new ConcurrentHashMap<>();
            for (int r = 1; r <= n; r++) {
                posPerRound.put(r, new ArrayList<>());
            }
            roundPositions.put(p, posPerRound);
        }

        this.playerTurn = 0;
        this.currentRound = 1;
        this.winners = new java.util.ArrayList<>();
        this.highestTotalScore = 0;
        notifyObservers();
    }

    public void generateNumber(String username, int y) {
        String expectedPlayer = players.get(playerTurn);
        if (!expectedPlayer.equals(username)) {
            throw new IllegalStateException("Nu este rândul tău!");
        }

        if (this.currentRound > this.n) {
            throw new IllegalStateException("Jocul s-a terminat deja!");
        }

        int currentPos = positions.getOrDefault(username, 0);
        int newPos = currentPos + y;
        int maxPos = 2 * n;
        int pointsGained = 0;

        if (newPos > maxPos) {
            pointsGained += 10;
            newPos = newPos % maxPos;
        }

        // Salvăm poziția în siguranță (structura e deja garantată să existe)
        roundPositions.get(username).get(currentRound).add(newPos);

        // Protecție la indexare în listă (dacă pică pe poziția 0, folosim ultima poziție din listă sau 0)
        int configIndex = newPos == 0 ? maxPos - 1 : newPos - 1;
        pointsGained += currentConfig.getNumbersList().get(configIndex);

        String occupiedBy = null;
        for (Map.Entry<String, Integer> entry : positions.entrySet()) {
            if (entry.getValue() == newPos && !entry.getKey().equals(username)) {
                occupiedBy = entry.getKey();
                break;
            }
        }

        if (occupiedBy != null) {
            pointsGained += (newPos / 2);
            int victimCurrentScore = scores.getOrDefault(occupiedBy, 0);
            scores.put(occupiedBy, victimCurrentScore + currentConfig.getNumbersList().get(configIndex) / 2);
        }

        scores.put(username, scores.getOrDefault(username, 0) + pointsGained);
        positions.put(username, newPos);

        this.lastActionMessage = String.format("%s a generat %d, a ajuns pe poz %d și a primit %d puncte.",
                username, y, newPos, pointsGained);

        ConcurrentHashMap<Integer, Integer> playerRounds = roundScores.get(username);
        playerRounds.put(currentRound, playerRounds.getOrDefault(currentRound, 0) + pointsGained);

        int nextTurn = (playerTurn + 1) % nrOfPlayers;

        if (nextTurn == 0) {
            if (this.currentRound >= this.n) {
                this.state = "finished";
                computeEndGameStats();
            } else {
                this.currentRound++;
            }
        }

        this.playerTurn = nextTurn;
        notifyObservers();
    }

    private void computeEndGameStats() {
        int maxScore = -1;
        List<String> currentWinners = new java.util.ArrayList<>();

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            int score = entry.getValue();
            if (score > maxScore) {
                maxScore = score;
                currentWinners.clear();
                currentWinners.add(entry.getKey());
            } else if (score == maxScore) {
                currentWinners.add(entry.getKey());
            }
        }

        this.highestTotalScore = maxScore;
        this.winners = currentWinners;

        int nrRounds = n;

        for (Map.Entry<String, ConcurrentHashMap<Integer, Integer>> entry : roundScores.entrySet()) {
            String playerName = entry.getKey();

            StringBuilder scoresString = new StringBuilder();
            StringBuilder roundPositionsString = new StringBuilder();
            long scorTotal = 0;
            for (int i = 1; i <= nrRounds; i++) {
                // Extragere scor per rundă în siguranță
                int scorRunda = entry.getValue().getOrDefault(i, 0);
                scorTotal = scorTotal + scorRunda;
                scoresString.append("Runda ").append(i).append(":").append(scorRunda).append("; ");

                // Extragere poziții în siguranță folosind playerName
                List<Integer> pozitiiRunda = null;
                if (this.roundPositions.get(playerName) != null) {
                    pozitiiRunda = this.roundPositions.get(playerName).get(i);
                }

                roundPositionsString.append("Runda ").append(i).append(":");
                if (pozitiiRunda != null && !pozitiiRunda.isEmpty()) {
                    roundPositionsString.append(pozitiiRunda.toString());
                } else {
                    roundPositionsString.append("[]");
                }
                roundPositionsString.append("; ");
            }

            StatisticiJoc statisticiJoc = new StatisticiJoc();
            statisticiJoc.setPlayerName(playerName);
            statisticiJoc.setPozitii(roundPositionsString.toString());
            statisticiJoc.setPunctaje(scoresString.toString());
            statisticiJoc.setPunctajCastigat(scorTotal);
            statisticiJoc.setNumeJoc("Joc: " + "Nigga" + scoresString);
            gameStatsService.save(statisticiJoc);
        }
    }
}