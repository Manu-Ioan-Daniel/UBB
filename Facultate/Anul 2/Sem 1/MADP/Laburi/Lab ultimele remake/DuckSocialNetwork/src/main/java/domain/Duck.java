package domain;

import enums.DuckType;

public class Duck extends User {
    private final DuckType type;
    private final Double speed;
    private final Double rezistance;
    public Duck(String username, String email, String password, DuckType type, Double speed, Double rezistance) {
        super(username, email, password);
        this.type = type;
        this.speed = speed;
        this.rezistance = rezistance;
    }

    public DuckType getType() {
        return type;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getRezistance() {
        return rezistance;
    }
}
