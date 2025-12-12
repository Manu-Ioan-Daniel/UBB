package validation;
import domain.Flock;
import errors.ValidationError;

public class FlockValidationStrategy implements ValidationStrategy<Flock> {
    @Override
    public void validate(Flock flock) {
        StringBuilder dbErrors = new StringBuilder();
        if(flock==null){
            dbErrors.append("Flock cannot be null.(it probably doesnt exist)\n");
            throw new ValidationError("Flock validation failed: "+dbErrors.toString());
        }
        if(flock.getFlockName()==null || flock.getFlockName().isEmpty()){
            dbErrors.append("Flock name cannot be null or empty.\n");
        }
        if(!dbErrors.isEmpty()) {
            throw new ValidationError("Flock validation failed: "+dbErrors.toString());
        }
    }
}