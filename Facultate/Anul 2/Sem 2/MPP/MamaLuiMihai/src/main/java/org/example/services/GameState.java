package org.example.services;

import lombok.Getter;
import lombok.Setter;
import org.example.observer.Observable;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class GameState extends Observable {

    private String status;
    private final int n = 2;
    private Map<String, Integer> playerScores = new ConcurrentHashMap<>();

    private static GameState instance;

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }

    public int getNrOfPlayers(){
        return playerScores.size();
    }

    public boolean addPlayer(String porecla){

        if(!playerScores.containsKey(porecla)){

            playerScores.put(porecla, 0);
            if(playerScores.size() == n){
                status = "ready";
                notifyObservers(this);
            }
            return true;
        }
        return false;
    }



}
