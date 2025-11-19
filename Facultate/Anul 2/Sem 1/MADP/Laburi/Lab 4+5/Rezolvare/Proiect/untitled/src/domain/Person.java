package domain;

public class Person extends User {
    private final String name;
    private final String surname;
    private final String occupation;
    private final String dateOfBirth;
    private final int empathyScore;
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
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", occupation='" + occupation + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", empathyScore=" + empathyScore +
                "} " + super.toString();
    }
}