package domain;

import enums.DuckType;

public class Duck extends User {
    private final DuckType type;
    private final Double speed;
    private final Double resistance;
    public Duck(String username, String email, String password, DuckType type, Double speed, Double resistance) {
        super(username, email, password);
        this.type = type;
        this.speed = speed;
        this.resistance = resistance;
    }

    public DuckType getType() {
        return type;
    }

    public Double getSpeed() {
        return speed;
    }

    public Double getResistance() {
        return resistance;
    }
}
