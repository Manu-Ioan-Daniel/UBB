package template.template.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import template.template.domain.SampleEntity;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@NoArgsConstructor
public class SampleGameState extends Observable {

    private final int n = 2;
    private final List<SampleEntity> loggedUsers = new ArrayList<>();
    private List<SampleEntity> sortedUsers = new ArrayList<>();
    private int nrOfPlayers = 0;
    private String currentPlayer;
    private int turnIndex = 0;
    private String chosenOne;
    private String generatedLetter;
    private int currentRound = 1;
    private boolean gameFinished;

    public void addEntity(SampleEntity sampleEntity) {
        loggedUsers.add(sampleEntity);
        nrOfPlayers++;

        if(nrOfPlayers == n){
            sortedUsers = loggedUsers.stream()
                    .sorted(Comparator.comparing(SampleEntity::getName))
                    .toList();
            chosenOne = sortedUsers.get(currentRound - 1).getName();
        }
        notifyObservers();
    }

    public boolean entityExists(SampleEntity sampleEntity) {
        return loggedUsers.stream().anyMatch(s->s.getName().equals(sampleEntity.getName()));
    }

    public void removePlayer(String username){
        if(loggedUsers.stream().noneMatch(s->s.getName().equals(username))){
            return;
        }
        loggedUsers.removeIf(s->s.getName().equals(username));
        nrOfPlayers--;
        if (nrOfPlayers < n) {
           resetState();
        }
        notifyObservers();
    }


    public void generateLetter(){

        java.util.Random random = new java.util.Random();
        char c = (char) (random.nextInt(26) + 'A');
        this.generatedLetter = String.valueOf(c);

        this.turnIndex = 0;
        this.currentPlayer = sortedUsers.get(this.turnIndex).getName();
        notifyObservers();
    }

    public void currentPlayerMoved(){

        this.turnIndex = (this.turnIndex+1) % n;
        if(turnIndex == 0)
            handleRoundEnd();
        else
            this.currentPlayer = sortedUsers.get(this.turnIndex).getName();

        notifyObservers();
    }

    public void handleRoundEnd(){
        if (currentRound == n) {
            this.gameFinished = true;
            this.currentPlayer = null;
            this.generatedLetter = null;
        } else {
            this.currentRound++;
            this.currentPlayer = null;
            this.generatedLetter = null;
            this.chosenOne = sortedUsers.get(currentRound - 1).getName();
        }
    }

    private void resetState() {
        chosenOne = null;
        sortedUsers = new ArrayList<>();
        currentPlayer = null;
        generatedLetter = null;
        turnIndex = 0;
        currentRound = 1;
        gameFinished = false;
    }
}
