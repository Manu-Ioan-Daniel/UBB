package org.example.services;

import lombok.Getter;
import lombok.Setter;
import org.example.models.Config;
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
    private Config currentConfig;

    public int getNrOfPlayers(){
        return playerScores.size();
    }

    public boolean addPlayer(String porecla){
        System.out.println("Adding player: " + porecla);
        if(!playerScores.containsKey(porecla)){

            System.out.println("Player adaugat cu porecla " + porecla);
            playerScores.put(porecla, 0);

            if(playerScores.size() == n)
                status = "ready";
            else
                status = "waiting";

            notifyObservers();
            return true;
        }
        return false;
    }

    public void removePlayer(String porecla){

        System.out.println("Removing player: " + porecla);
        if(playerScores.containsKey(porecla)){
            playerScores.remove(porecla);
            status = "waiting";
            notifyObservers();
        }
    }



}
