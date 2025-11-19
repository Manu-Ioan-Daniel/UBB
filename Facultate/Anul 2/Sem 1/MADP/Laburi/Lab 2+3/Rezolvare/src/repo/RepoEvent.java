package repo;
import events.Event;
import errors.RepoError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoEvent {
    private List<Event> events = new ArrayList<>();
    public void addEvent(Event event){
        if(events.contains(event))
            throw new RepoError("Event already exists!");
        events.add(event);
    }
    public void removeEvent(Event event){
        events.remove(event);
    }
    public List<Event> getAll(){
        return events;
    }

    public List<Event> getAllEvents() {
        return Collections.unmodifiableList(events);
    }
}
