package validation;

import domain.Duck;
import domain.Person;
import domain.User;
import exceptions.ValidationException;

import java.time.LocalDate;

public class UserValidator implements Validator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        StringBuilder errors =  new StringBuilder();
        if(entity.getPassword().length()<6){
            errors.append("Password length should be at least 6 characters\n");
        }
        if(!entity.getEmail().contains("@")){
            errors.append("Username should contain @\n");
        }
        if(entity instanceof Duck duck){
            if(duck.getResistance()<0){
                errors.append("Resistance should be at least 0\n");
            }
            if(duck.getSpeed()<0){
                errors.append("Speed should be at least 0\n");
            }

        }else if(entity instanceof Person person){
            if(person.getDateOfBirth().isAfter(LocalDate.now())){
                errors.append("Date of Birth cannot be past current date\n");
            }
            if(person.getEmpathyLevel() < 0 || person.getEmpathyLevel() > 10){
                errors.append("Empathy level must be between 0 and 10");
            }
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors.toString());
        }
    }
}
