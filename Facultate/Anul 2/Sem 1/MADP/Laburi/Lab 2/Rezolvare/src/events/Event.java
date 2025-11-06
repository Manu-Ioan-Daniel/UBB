package events;

import domain.User;

import java.util.ArrayList;
import java.util.List;

public abstract class Event {
    private final String name;
    private final Long id;
    private List<User> subscribers = new ArrayList<>();

    public Event(Long id,String name) {
        this.id=id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void subscribe(User user) {
        if (!subscribers.contains(user))
            subscribers.add(user);
    }

    public void unsubscribe(User user) {
        subscribers.remove(user);
    }
    public Long getId(){
        return id;
    }
    public void notifySubscribers(String message) {
        for (User u : subscribers) {
            if (u != null) {
                u.receiveMessage(message);
            }
        }
    }
}
