package event;
import domain.User;
import observer.Observable;
import observer.Observer;

import java.util.List;

public class Event extends Observable {
    private Long id;
    private String name;
    public Event(Long id,String name, List<User> users) {
        this.id=id;
        this.name=name;
        for(User user : users) subscribe(user);
    }
    public Event(String name, List<User> users) {
        this.name=name;
        for(User user : users) subscribe(user);
    }
    public Event(String name) {
        this.name=name;
    }
    public Event(Long id,String name) {
        this.id=id;
        this.name=name;
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
    public String getName() {
        return name;
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
