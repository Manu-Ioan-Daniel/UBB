package org.example.observer;

import org.example.services.GameState;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers(GameState gameState){
        for (Observer o : observers) {
            o.update(gameState);
        }
    }

}
