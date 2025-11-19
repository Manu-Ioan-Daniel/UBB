package domain;

public class Person extends User {
    private String name;
    private String surname;
    private String occupation;
    private String dateOfBirth;
    private int empathyScore;
    public Person(Long id, String username, String email, String password, String name, String surname, String occupation, String dateOfBirth,int emphatyScore) {
        super(id, username, email, password);
        this.name = name;
        this.surname = surname;
        this.occupation = occupation;
        this.dateOfBirth = dateOfBirth;
        this.empathyScore=emphatyScore;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getOccupation() {
        return occupation;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public int getEmpathyScore(){
        return empathyScore;
    }
}
