package observer;

import java.util.ArrayList;
import java.util.List;
public class Observable {
    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    protected void notifyObservers() {
        for (Observer obs : observers) {
            obs.update();
        }
    }
}
