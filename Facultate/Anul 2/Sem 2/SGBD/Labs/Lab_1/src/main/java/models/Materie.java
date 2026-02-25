package models;

public class Materie extends Entity<Long> {

    private final String name;
    private final int credits;

    public Materie(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }
}
