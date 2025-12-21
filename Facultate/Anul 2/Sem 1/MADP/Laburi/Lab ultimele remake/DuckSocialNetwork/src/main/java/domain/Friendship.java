package domain;

import utils.containers.Tuple;

public class Friendship extends Entity<Tuple<Long,Long>> {
    private final String status;
    public Friendship(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
