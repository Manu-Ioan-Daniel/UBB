package domain;

import enums.DuckType;

public class Duck extends User{
    private final DuckType type;
    private final double speed;
    private final double rezistance;
    public Duck(Long id, String username, String email, String password, DuckType type, double speed, double rezistance) {
        super(id, username, email, password);
        this.type = type;
        this.speed = speed;
        this.rezistance = rezistance;
    }
    public Duck(String username, String email, String password, DuckType type, double speed, double rezistance) {
        super(username, email, password);
        this.type = type;
        this.speed = speed;
        this.rezistance = rezistance;
    }
    public DuckType getType() {
        return type;
    }

    public double getSpeed() {
        return speed;
    }

    public double getRezistance() {
        return rezistance;
    }
    @Override
    public String toString() {
        return "Duck{" +
                "type=" + type +
                ", speed=" + speed +
                ", rezistance=" + rezistance +
                "} " + super.toString();
    }

}