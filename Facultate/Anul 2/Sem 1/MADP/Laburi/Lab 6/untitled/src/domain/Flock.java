package domain;

import java.util.ArrayList;
import java.util.List;

public class Flock{
    private final Long id;
    private final String flockName;
    private List<Duck> members=new ArrayList<>();
    public Flock(Long id, String flockName) {
        this.id = id;
        this.flockName = flockName;
    }
    public Flock(Long id, String flockName, List<Duck> members) {
        this.id = id;
        this.flockName = flockName;
        this.members = members;
    }

    public Long getId() {
        return id;
    }
    public String getFlockName(){
        return flockName;
    }
    public double getMedianPerformance(){
        if(members==null || members.isEmpty()){
            return 0;
        }
        double total=0;
        for(Duck duck:members){
            total+=duck.getRezistance()+duck.getSpeed();
        }
        return total/(members.size()*2);
    }
    public List<Duck> getMembers() {
        return members;
    }
    public void addDuck(Duck duck){
        this.members.add(duck);
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Flock{id=").append(id).append(", flockName=").append(flockName).append(", members:");
        for(Duck duck:members){
            sb.append("\n\t").append(duck.toString());
        }
        sb.append("\n}");
        return sb.toString();
    }
}