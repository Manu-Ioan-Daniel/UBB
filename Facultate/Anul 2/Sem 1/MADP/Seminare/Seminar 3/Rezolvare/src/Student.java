import java.util.Objects;

public class Student extends Entity<Integer> {
    private String nume;
    private float media;
    public Student(String nume, float media) {
        super(-1);
        this.nume = nume;
        this.media = media;
    }
    public Student(Integer id, String nume, float media) {
        super(id);
        this.nume = nume;
        this.media = media;
    }
    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", media=" + media +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Student other){
            return this.nume.equals(other.nume) && this.media==other.media;
        }
        return false;
    }
    @Override
    public int hashCode() {
        return Objects.hash(this.nume, this.media);
    }

    public String getNume() {
        return nume;
    }

    public float getMedia() {
        return media;
    }
}
