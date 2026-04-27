package models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "projects")
public class Project extends models.Entity<Long> {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private java.sql.Date startDate;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Materie department;

    public Project() {}

    public Project(String name, String description, java.sql.Date startDate, Materie department) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.department = department;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public java.sql.Date getStartDate() { return startDate; }
    public Materie getDepartment() { return department; }
}

