package models;

import java.io.Serializable;
import java.util.Objects;

public class NotaId implements Serializable {

    private Long student;
    private Long materie;

    public NotaId() {}

    public NotaId(Long student, Long materie) {
        this.student = student;
        this.materie = materie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotaId notaId = (NotaId) o;
        return Objects.equals(student, notaId.student) &&
                Objects.equals(materie, notaId.materie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, materie);
    }
}