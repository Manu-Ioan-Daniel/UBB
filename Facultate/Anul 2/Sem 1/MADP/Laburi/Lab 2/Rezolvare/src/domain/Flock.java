package domain;

import java.util.List;

public class Flock {
    private Long id;
    private String flockName;
    private List<Duck> members;

    public Flock(Long id, String flockName) {
        this.id = id;
        this.flockName = flockName;
    }

    public Long getId() {
        return id;
    }
    public String getFlockName(){
        return flockName;
    }

}
