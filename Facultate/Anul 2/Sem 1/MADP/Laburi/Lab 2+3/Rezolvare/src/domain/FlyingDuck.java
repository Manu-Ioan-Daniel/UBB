package domain;

import interfaces.Zburator;

public class FlyingDuck extends Duck implements Zburator {
    @Override
    public void zboara() {
        System.out.println("Rata zboara");
    }
    public FlyingDuck(Long id, String username, String email, String password, double speed, double rezistance) {
        super(id, username, email, password, enums.DuckType.FLYING, speed, rezistance);
    }

}
