package models;

import enums.Hobby;

import java.util.List;

public class Client extends Entity<Long>{

    private final String name;
    private final int fidelityGrade;
    private final int age;
    private final List<Hobby> hobbies;

    public Client(String name, int fidelityGrade, int age, List<Hobby> hobbies) {
        this.name = name;
        this.fidelityGrade = fidelityGrade;
        this.age = age;
        this.hobbies = hobbies;
        Object c;

    }

    public String getName() {
        return name;
    }

    public int getFidelityGrade() {
        return fidelityGrade;
    }

    public int getAge() {
        return age;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

}

