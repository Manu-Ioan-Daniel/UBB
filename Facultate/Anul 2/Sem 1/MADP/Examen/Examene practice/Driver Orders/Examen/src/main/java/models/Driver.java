package models;

public class Driver extends Entity<Long> {
    private final String name;

    public Driver(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

}
