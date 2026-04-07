package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "materii")
public class Materie extends models.Entity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private int credits;

    public Materie() {}

    public Materie(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }
}