package validation;

import domain.Person;
import errors.ValidationError;

public class PersonValidationStrategy implements ValidationStrategy<Person> {
    @Override
    public void validate(Person entity) {
        StringBuilder dbErrors = new StringBuilder();
        if(entity==null){
            dbErrors.append("Person cannot be null.(it probably doesnt exist)\n ");
            throw new ValidationError("Person validation failed:\n"+dbErrors.toString());
        }
        if(entity.getId()==null || entity.getId()<=0){
            dbErrors.append("Invalid ID\n ");
        }
        if(entity.getUsername()==null || entity.getUsername().isEmpty()){
            dbErrors.append("Invalid username\n ");
        }
        if(entity.getEmail()==null || !entity.getEmail().contains("@")) {
            dbErrors.append("Invalid email\n ");
        }
        if(entity.getPassword()==null || entity.getPassword().isEmpty()){
            dbErrors.append("Password must be at least 1 character long\n ");
        }
        if(entity.getName()==null || entity.getName().isEmpty()){
            dbErrors.append("Invalid name\n ");
        }
        if(entity.getSurname()==null || entity.getSurname().isEmpty()) {
            dbErrors.append("Invalid surname\n ");
        }
        if(entity.getOccupation()==null || entity.getOccupation().isEmpty()){
            dbErrors.append("Invalid occupation\n ");
        }
        if(entity.getDateOfBirth()==null || entity.getDateOfBirth().isEmpty()) {
            dbErrors.append("Invalid date of birth\n ");
        }
        if(entity.getEmpathyScore()<0 || entity.getEmpathyScore()>100){
            dbErrors.append("Empathy score must be between 0 and 100\n ");
        }
        if(!dbErrors.isEmpty()){
            throw new ValidationError("Person validation failed:\n"+dbErrors.toString());
        }
    }

}
