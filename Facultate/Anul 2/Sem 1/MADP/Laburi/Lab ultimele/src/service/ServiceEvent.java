package service;

import domain.User;
import errors.ServiceError;
import errors.ValidationError;
import event.Event;
import event.RaceEvent;
import repo.DatabaseEventRepository;
import validation.RaceEventValidationStrategy;
import validation.ValidationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceEvent {
    DatabaseEventRepository repoEvent;
    private final Map<Class<? extends Event>, ValidationStrategy<? extends Event>> validators=new HashMap<>();
    public ServiceEvent(DatabaseEventRepository repoEvent) {
        this.repoEvent = repoEvent;
        validators.put(Event.class, event -> {
            if(event.getId()==null || event.getId()<=0){
                throw new IllegalArgumentException("Invalid event id!");
            }
        });
        validators.put(RaceEvent.class, new RaceEventValidationStrategy());
    }
    @SuppressWarnings("unchecked")
    public void addEvent(Event event){
        ValidationStrategy<Event> validator=(ValidationStrategy<Event>) validators.get(event.getClass());
        validator.validate(event);
        repoEvent.addEvent(event);
    }
    public void startEvent(String eventName) {
        if(eventName==null || eventName.isEmpty()){
            throw new ValidationError("Invalid event name!");
        }
        Event event = repoEvent.getEventByName(eventName);
        if (event == null) {
            throw new ServiceError("Nu exista event cu acel id!");
        }
        if (event instanceof RaceEvent raceEvent) {
            raceEvent.startRace();
        }
    }
    public void removeEvent(String eventName) {
        if(eventName==null || eventName.isEmpty())
           throw new ValidationError("Invalid event name!");
        repoEvent.removeEvent(eventName);
    }
    public List<Event> getAllEvents(){
        return repoEvent.getAllEvents();
    }
//    public Event getEventById(Long id){
//        if(id==null || id<=0){
//            throw new ValidationError("Invalid event id!");
//        }
//        return repoEvent.getEventById(id);
//    }

}
