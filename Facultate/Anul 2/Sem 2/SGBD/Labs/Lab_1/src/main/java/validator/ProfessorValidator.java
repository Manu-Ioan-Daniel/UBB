package validator;

import exceptions.ValidationException;
import models.Professor;

public class ProfessorValidator implements Validator<Professor>{
    @Override
    public void validate(Professor professor) throws ValidationException {
        StringBuilder sb = new StringBuilder();
        if(professor.getName() == null || professor.getName().isEmpty()){
            sb.append("Numele nu poate fi null sau gol!\n");
        }
        if(professor.getAge()< 18 || professor.getAge() == null){
            sb.append("Varsta trebuie sa fie minim 18 ani!\n");
        }
        if(professor.getEmail() == null || professor.getEmail().isEmpty() || !professor.getEmail().contains("@")){
            sb.append("Email in format invalid!\n");
        }
        if(professor.getMaterieId() == null){
            sb.append("Profesorul trebuie sa predea o materie!\n");
        }
        if(!sb.isEmpty()){
            throw new ValidationException(sb.toString());
        }
    }
}
