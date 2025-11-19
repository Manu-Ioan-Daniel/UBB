package domain;

import enums.DuckType;

public abstract class Duck extends User{
    private DuckType type;
    private double speed;
    private double rezistance;
    private Flock<Duck> flock;
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

    public void setFlock(Flock<Duck> flock) {
        this.flock = flock;
    }

    public Flock<Duck> getFlock() {
        return flock;
    }
    @Override
    public String toString() {
        return "Duck{" +
                "type=" + type +
                ", speed=" + speed +
                ", rezistance=" + rezistance +
                ", flock=" + flock.getFlockName() +
                "} " + super.toString();
    }

}
