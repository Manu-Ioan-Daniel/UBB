package models;

import jakarta.persistence.*;
import jakarta.persistence.Entity;

@Entity
@Table(name = "note_studenti")
@IdClass(NotaId.class)
public class Nota{

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "materie_id")
    private Materie materie;

    @Column(name = "nota")
    private Long nota;

    public Nota() {}

    public Nota(Student student, Materie materie, Long nota) {
        this.student = student;
        this.materie = materie;
        this.nota = nota;
    }

    public Long getNota() {
        return nota;
    }

    public Student getStudent() {
        return student;
    }

    public Materie getMaterie() {
        return materie;
    }
}