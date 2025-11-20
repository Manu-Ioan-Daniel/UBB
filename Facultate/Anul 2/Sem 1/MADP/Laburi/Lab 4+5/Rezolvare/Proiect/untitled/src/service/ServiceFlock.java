package service;
import domain.Duck;
import domain.Flock;
import errors.ValidationError;
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
        if(flockId == null || flockId <= 0){
            throw new ValidationError("Invalid flock id!");
        }
        return repoFlock.getFlockById(flockId);
    }

    public List<Flock> getFlocks() {
        return repoFlock.getFlocks();
    }
}
