package event;
import domain.Duck;

import java.util.List;

public class RaceEvent extends Event{
    private final int M;
    public RaceEvent(Long id,String name,List<Duck> ducks,int M) {
        super(id,name);
        this.M=M;
        chooseMembers(ducks);
    }
    public RaceEvent(Long id,String name,int M){super(id,name);this.M=M;}
    public RaceEvent(String name, List<Duck> ducks,int M) {
        super(name);
        this.M=M;
        chooseMembers(ducks);
    }
    public void startRace(){
        System.out.println("Race Started");
        this.notifySubscribers();
    }
    private void chooseMembers(List<Duck> ducks){
        ducks.sort((d1, d2) -> Double.compare(d1.getRezistance(), d2.getRezistance()));
        if(ducks.size()<M){
            throw new IllegalArgumentException("Not enough ducks to choose from");
        }
        for(int i=0;i<M;i++){
            this.subscribe(ducks.get(i));
        }
    }

    public int getM() {
        return M;
    }

}
