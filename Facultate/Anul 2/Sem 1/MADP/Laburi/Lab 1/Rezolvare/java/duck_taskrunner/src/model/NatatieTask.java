package model;
import algorithm.MyAlgorithm;
import reader.NatatieReader;

public class NatatieTask extends Task {
    private final MyAlgorithm algorithm;
    private NatatieData data;
    public NatatieTask(String taskID, String description, MyAlgorithm algorithm){
        super(taskID,description);
        this.algorithm = algorithm;
    }
    public NatatieData getData() {
        return data;
    }
    @Override
    public void execute() {
        this.data= NatatieReader.readData();
        if(this.data==null){
            System.out.println("No data available!");
            return;
        }
        algorithm.executeStrategy(this);
    }
}
