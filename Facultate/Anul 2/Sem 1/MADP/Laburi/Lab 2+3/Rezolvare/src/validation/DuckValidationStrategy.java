package validation;

import domain.Duck;
import domain.User;
import enums.DuckType;
import errors.ValidationError;

public class DuckValidationStrategy extends UserValidationStrategy {
    @Override
    public void validate(User user) {
        super.validate(user);
        String errors="";
        Duck duck = (Duck) user;
        if (duck.getType() == null || duck.getType()!= DuckType.FLYING && duck.getType()!=DuckType.SWIMMING && duck.getType()!=DuckType.FLYING_AND_SWIMMING) {
           errors+="Duck type is invalid!";
        }
        if (duck.getSpeed() <= 0) {
            errors+="Duck speed must be greater than 0!";
        }
        if (duck.getRezistance() <= 0) {
            errors += "Duck rezistance must be greater than 0!";
        }
        if(duck.getFlock()==null){
            errors+="Duck must belong to a flock!";
        }
        if(!errors.isEmpty()){
            throw new ValidationError(errors);
        }

    }

}
