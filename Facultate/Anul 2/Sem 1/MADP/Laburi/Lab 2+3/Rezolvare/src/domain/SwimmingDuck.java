package domain;

import interfaces.Inotator;

public class SwimmingDuck extends Duck implements Inotator {
    @Override
    public void inoata() {
        System.out.println("Rata inoata");
    }
    public SwimmingDuck(Long id, String username, String email, String password, double speed, double rezistance) {
        super(id, username, email, password, enums.DuckType.SWIMMING, speed, rezistance);
    }

}
