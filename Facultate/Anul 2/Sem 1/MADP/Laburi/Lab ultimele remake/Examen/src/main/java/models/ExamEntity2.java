package models;

public class ExamEntity2 extends Entity<Long> {
    private final String name;

    public ExamEntity2(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
