package validation;
import domain.Duck;
import domain.Flock;
import errors.ValidationError;

public class FlockValidationStrategy implements ValidationStrategy<Flock<? extends Duck>> {
    @Override
    public void validate(Flock<? extends Duck> flock) {
        StringBuilder sb = new StringBuilder();
        if(flock.getFlockName()==null || flock.getFlockName().isEmpty()){
            sb.append("Flock name cannot be null or empty.\n");
        }
        if(flock.getId()<0){
            sb.append("Flock ID must be non-negative.\n");
        }
        if(!sb.isEmpty()) {
            throw new ValidationError(sb.toString());
        }
    }
}
