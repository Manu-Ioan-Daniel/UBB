package event;
import domain.User;
import observer.Observable;
import java.util.List;

public class Event extends Observable {
    public Event(List<User> users) {
        for(User user : users) subscribe(user);
    }
    public Event(){}
    public void subscribe(User user){
        this.addObserver(user);
    }
    public void unsubscribe(User user){
        this.removeObserver(user);
    }
    public void notifySubscribers(){
        this.notifyObservers();
    }

}
