package template.template.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import template.template.domain.SampleEntity;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Component;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@NoArgsConstructor
public class SampleGameState extends Observable {

    private String sampleString;
    private final ConcurrentHashMap<String, Object> concurrentHashMap = new ConcurrentHashMap<>(); //way to store items
    private int nrOfPlayers = 0;
    public void setSampleString(String sampleString) {
        this.sampleString = sampleString;
        notifyObservers();
    }

    public void addEntity(SampleEntity sampleEntity) {
        concurrentHashMap.put(sampleEntity.getName(), "mata");
        nrOfPlayers++;
        notifyObservers();
    }

    public boolean entityExists(SampleEntity sampleEntity) {
        return concurrentHashMap.containsValue(sampleEntity.getName());
    }

    public void removePlayer(String username){
        if(!concurrentHashMap.containsKey(username)){
            return;
        }
        concurrentHashMap.remove(username);
        nrOfPlayers--;
        notifyObservers();
    }
}
