package domain;

import enums.DuckType;

public class Duck extends User{
    private DuckType type;
    private double speed;
    private double rezistance;
    private Flock flock;
    public Duck(Long id, String username, String email, String password, DuckType type, double speed, double rezistance,Flock flock) {
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

    public Flock getFlock() {
        return flock;
    }
    @Override
    public String toString() {
        return "domain.Duck{" +
                "type=" + type +
                ", speed=" + speed +
                ", rezistance=" + rezistance +
                ", flock=" + flock +
                "} " + super.toString();
    }
//    @Override
//    public void sendMessage(){
//        return;
//    }

}
