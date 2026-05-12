package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import java.util.List;

@Entity
@Table(name = "materii")
public class Materie extends models.Entity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "credits")
    private int credits;

    @OneToMany(mappedBy = "materie", fetch = FetchType.LAZY)
    private List<Nota> notas;

    @OneToMany(mappedBy = "materie", fetch = FetchType.LAZY)
    private List<models.Project> projects;

    public Materie() {}

    public Materie(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }

    public List<Nota> getNotas() { return notas; }
    public List<models.Project> getProjects() { return projects; }
}