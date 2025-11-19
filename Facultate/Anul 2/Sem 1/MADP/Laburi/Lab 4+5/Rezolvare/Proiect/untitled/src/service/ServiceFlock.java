package service;
import domain.Duck;
import domain.Flock;
import repo.DatabaseFlockRepository;
import validation.DuckValidationStrategy;
import validation.FlockValidationStrategy;
import java.util.List;

public class ServiceFlock {
    private final DatabaseFlockRepository repoFlock;
    private final FlockValidationStrategy flockValidationStrategy;
    private final DuckValidationStrategy duckValidationStrategy;
    public ServiceFlock(DatabaseFlockRepository repoFlock) {
        this.repoFlock = repoFlock;
        this.flockValidationStrategy = new FlockValidationStrategy();
        this.duckValidationStrategy = new DuckValidationStrategy();
    }

    public void addFlock(Flock flock) {
        flockValidationStrategy.validate(flock);
        repoFlock.addFlock(flock);
    }

    public void addDuckToFlock(Flock flock, Duck duck) {
        flockValidationStrategy.validate(flock);
        duckValidationStrategy.validate(duck);
        repoFlock.addDuckToFlock(flock, duck);
    }

    public List<Flock> getAllFlocks() {
        return repoFlock.getFlocks();
    }

    public Flock getFlockById(Long flockId) {
        List<Flock> flocks = repoFlock.getFlocks();
        for(Flock flock:flocks){
            if(flock.getId().equals(flockId)){
                return flock;
            }
        }
        return null;
    }

    public List<Flock> getFlocks() {
        return repoFlock.getFlocks();
    }
}
