public class ValidatorStudenti {
    public static void valideazaStudent(Student student) {
        String errors="";
        if(student.getNume().isEmpty()){
            errors+="Numele nu poate fi vid!\n";
        }
        if(student.getMedia()<0 || student.getMedia()>10){
            errors+="Media trebuie sa fie intre 0 si 10!\n";
        }
        if(!errors.isEmpty()){
            throw new ValidationException(errors);
        }
    }
}
