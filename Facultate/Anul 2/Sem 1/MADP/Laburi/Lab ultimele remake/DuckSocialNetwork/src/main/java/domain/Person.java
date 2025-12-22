package domain;

import java.time.LocalDate;


public class Person extends User{
    private final String name;
    private final String surname;
    private final LocalDate dateOfBirth;
    private final String occupation;
    private final int empathyLevel;

    public Person(String username, String email, String password, String name, String surname, LocalDate dateOfBirth, String ocupatie, int nivelEmpatie) {
        super(username, email, password);
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.occupation = ocupatie;
        this.empathyLevel = nivelEmpatie;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getOccupation() {
        return occupation;
    }

    public int getEmpathyLevel() {
        return empathyLevel;
    }
}
