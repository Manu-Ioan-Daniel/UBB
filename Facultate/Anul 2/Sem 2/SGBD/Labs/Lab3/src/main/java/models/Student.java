package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "studenti")
public class Student extends models.Entity<Long>{

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    public Student() {

    }
    
    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }
}
