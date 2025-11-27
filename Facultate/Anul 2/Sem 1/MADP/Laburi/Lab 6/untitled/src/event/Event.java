package event;
import domain.User;
import observer.Observable;
import observer.Observer;

import java.util.List;

public class Event extends Observable {
    private final Long id;
    public Event(Long id,List<User> users) {
        this.id=id;
        for(User user : users) subscribe(user);
    }
    public Event(Long id){
        this.id=id;
    }
    public void subscribe(User user){
        this.addObserver(user);
    }
    public void unsubscribe(User user){
        this.removeObserver(user);
    }
    public void notifySubscribers(){
        this.notifyObservers();
    }
    public Long getId() {
        return id;
    }
    @Override
    public String toString() {
        StringBuilder sb=new StringBuilder();
        sb.append("Event{id=").append(id).append(", subscribers:");
        for(Observer obs:this.getObservers()){
            sb.append("\n\t").append(obs.toString());
        }
        return sb.toString();
    }
}
