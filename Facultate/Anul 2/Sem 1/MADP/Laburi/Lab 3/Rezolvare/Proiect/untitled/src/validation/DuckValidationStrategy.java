package validation;

import domain.Duck;
import errors.ValidationError;

public class DuckValidationStrategy implements ValidationStrategy<Duck> {
    @Override
    public void validate(Duck duck) {
        StringBuilder dbErrors = new StringBuilder();
        if(duck==null){
            dbErrors.append("Duck cannot be null(it probably doesnt exist)\n ");
            throw new ValidationError("Duck validation failed:\n"+dbErrors.toString());
        }
        if(duck.getId()==null || duck.getId()<=0){
            dbErrors.append("Invalid ID\n ");
        }
        if(duck.getUsername()==null || duck.getUsername().isEmpty()){
            dbErrors.append("Invalid username\n ");
        }
        if(duck.getEmail()==null || !duck.getEmail().contains("@")) {
            dbErrors.append("Invalid email\n ");
        }
        if(duck.getPassword()==null || duck.getPassword().isEmpty()){
            dbErrors.append("Password must be at least 1 character long\n ");
        }
        if(duck.getRezistance()<0) {
            dbErrors.append("Rezistance must be non-negative\n ");
        }
        if(duck.getSpeed()<0){
            dbErrors.append("Speed must be non-negative\n ");
        }
        if(!dbErrors.isEmpty()){
            throw new ValidationError("Duck validation failed:\n"+dbErrors.toString());
        }
    }
}
