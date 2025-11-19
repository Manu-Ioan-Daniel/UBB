package validation;

import domain.Person;
import domain.User;
import errors.ValidationError;

public class PersonValidationStrategy extends UserValidationStrategy {
    @Override
    public void validate(User user){
        super.validate(user);
        String errors="";
        Person p = (Person) user;
        if(p.getName()==null || p.getName().isEmpty()){
            errors+="Person name is invalid!";
        }
        if(p.getDateOfBirth()==null || p.getDateOfBirth().isEmpty()){
            errors+="Person date of birth is invalid!";
        }
        if(p.getSurname()==null || p.getSurname().isEmpty()){
            errors+="Person surname is invalid!";
        }
        if(p.getEmpathyScore()<0 || p.getEmpathyScore()>10){
            errors+="Person empathy score must be between 0 and 10!";
        }
        if(!errors.isEmpty()){
            throw new ValidationError(errors);
        }

    }
}
