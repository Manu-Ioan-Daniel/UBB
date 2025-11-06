package events;
import domain.SwimmingDuck;
import domain.User;
import errors.ServiceError;
import repo.RepoUser;

import java.util.List;
import java.util.stream.Collectors;

public class RaceEvent extends Event {
    private final int M;
    public RaceEvent(Long id,String name, int M) {
        super(id,name);
        this.M = M;
    }

    // selectează M rațe înotătoare dintr-un list de ducks
    public List<SwimmingDuck> selectParticipants(List<User> users) {
        return users.stream()
                .filter(d -> d instanceof SwimmingDuck)
                .map(d -> (SwimmingDuck)d)
                .sorted((d1, d2) -> {
                    int cmp = Double.compare(d1.getRezistance(), d2.getRezistance());
                    if (cmp != 0) return cmp;
                    return Double.compare(d1.getSpeed(), d2.getSpeed());
                })
                .limit(M)
                .collect(Collectors.toList());
    }
    public void startRace(List<User> users) {
        List<SwimmingDuck> participants = selectParticipants(users);
        if(participants.size()<M){
            throw new ServiceError("Nu sunt destule rate pentru a incepe evenimentul!");
        }
        for(SwimmingDuck duck : participants) {
            subscribe(duck);
        }
        notifySubscribers("Race " + getName() + " started with " + participants.size() + " participants!");
    }
}
