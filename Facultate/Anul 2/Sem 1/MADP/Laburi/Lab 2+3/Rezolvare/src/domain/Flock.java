package domain;

import java.util.List;

public class Flock<T extends Duck> {
    private Long id;
    private String flockName;
    private List<T> members;

    public Flock(Long id, String flockName) {
        this.id = id;
        this.flockName = flockName;
    }
    public Flock(Long id, String flockName, List<T> members) {
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
    public List<T> getMembers() {
        return members;
    }
    public void addDuck(T duck){
        this.members.add(duck);
    }
    @Override
    public String toString(){
        StringBuilder sb=new StringBuilder();
        sb.append("Flock{id=").append(id).append(", flockName=").append(flockName).append(", members:");
        for(T duck:members){
            sb.append("\n\t").append(duck.toString());
        }
        sb.append("\n}");
        return sb.toString();
    }

}
