public class Student {
    private int id;
    private String nume;
    private int media;
    public Student(int id, String nume, int media) {
        this.id = id;
        this.nume = nume;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public int getMedia() {
        return media;
    }
}
