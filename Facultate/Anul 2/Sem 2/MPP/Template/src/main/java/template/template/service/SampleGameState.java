package template.template.service;

import lombok.Getter;
import lombok.NoArgsConstructor;
import template.template.domain.Config;
import template.template.domain.Player;
import template.template.utils.Observer.Observable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@NoArgsConstructor
public class SampleGameState extends Observable {

    private final ConcurrentHashMap<String, Object> players = new ConcurrentHashMap<>(); //way to store items
    private int nrOfPlayers = 0;
    private final int n = 2;
    private String state;
    private String chosenOne;
    private List<Config> configs;


    public void addPlayer(Player player) {
        players.put(player.getName(), "mata");
        nrOfPlayers++;
        if(nrOfPlayers == n){
            this.state = "ready";
            chosenOne = players.keySet().toArray()[n/2].toString();
        }else{
            this.state = "not_ready";
        }
        notifyObservers();
    }

    public boolean playerExists(Player player) {
        return players.containsValue(player.getName());
    }

    public void removePlayer(String username){
        if(!players.containsKey(username)){
            return;
        }
        players.remove(username);
        nrOfPlayers--;
        notifyObservers();
    }
}
