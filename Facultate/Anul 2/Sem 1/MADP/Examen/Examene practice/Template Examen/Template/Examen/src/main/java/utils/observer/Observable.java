package utils.observer;

import enums.ChangeEvent;
import java.util.ArrayList;
import java.util.List;

public class Observable {
    final List<Observer> observers =  new ArrayList<>();
    public void addObserver(Observer observer){
        observers.add(observer);
    }
    public void removeObserver(Observer observer){
        observers.remove(observer);
    }
    public void notifyObservers(ChangeEvent event){
        for(Observer observer:observers){
            observer.update(event);
        }
    }
    public List<Observer> getObservers(){
        return observers;
    }
}
