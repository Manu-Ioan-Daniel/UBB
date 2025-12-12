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

    public void addDuckToFlock(String flockName, String duckName) {
        if(flockName == null || flockName.isEmpty()){
            throw new ValidationError("Invalid flock name!");
        }
        if(duckName == null || duckName.isEmpty()){
            throw new ValidationError("Invalid duck name!");
        }
        repoFlock.addDuckToFlock(flockName, duckName);
    }

    public List<Flock> getAllFlocks() {
        return repoFlock.getFlocks();
    }

//    public Flock getFlockById(Long flockId) {
//        if(flockId == null || flockId <= 0){
//            throw new ValidationError("Invalid flock id!");
//        }
//        return repoFlock.getFlockById(flockId);
//    }

    public List<Flock> getFlocks() {
        return repoFlock.getFlocks();
    }
}
