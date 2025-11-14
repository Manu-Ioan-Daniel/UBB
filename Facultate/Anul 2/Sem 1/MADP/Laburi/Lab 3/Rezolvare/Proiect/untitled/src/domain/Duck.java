package domain;

import enums.DuckType;

public class Duck extends User{
    private final DuckType type;
    private final double speed;
    private final double rezistance;
    private Long flockId;
    public Duck(Long id, String username, String email, String password, DuckType type, double speed, double rezistance) {
        super(id, username, email, password);
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

    public void setFlock(Long flockId) {
        this.flockId = flockId;
    }

    public Long getFlockId() {
        return flockId;
    }
    @Override
    public String toString() {
        return "Duck{" +
                "type=" + type +
                ", speed=" + speed +
                ", rezistance=" + rezistance +
                ", flockId=" + flockId +
                "} " + super.toString();
    }

}