package service;

import domain.User;
import errors.ServiceError;
import events.Event;
import events.RaceEvent;
import repo.RepoEvent;
import repo.RepoUser;
import validation.EventValidationStrategy;
import validation.ValidationStrategy;
import events.Event;
import java.util.List;

public class ServiceEvent {
    private RepoEvent repo;
    private RepoUser repoUser;
    private ValidationStrategy<Event> validationStrategy;
    public ServiceEvent(RepoEvent repo,RepoUser repoUser) {
        this.repo = repo;
        this.validationStrategy=new EventValidationStrategy();
        this.repoUser=repoUser;
    }
    public void addEvent(Event event){
        validationStrategy.validate(event);
        repo.addEvent(event);
    }
    public void runEvent(){
        List<Event> events=repo.getAllEvents();
        if(events.isEmpty()){
            throw new ServiceError("No events available to run!");
        }
        Event event=events.get(events.size()-1);
        if(event instanceof RaceEvent re){
            re.startRace(repoUser.getAllUsers());
            repo.removeEvent(event);
        }
    }

}
