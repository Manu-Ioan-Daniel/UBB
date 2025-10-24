package model;
import algorithm.MyAlgorithm;
import reader.NatatieReader;

public class NatatieTask extends Task {
    private final MyAlgorithm algorithm;
    private NatatieData data;
    private String file;
    public NatatieTask(String taskID, String description, MyAlgorithm algorithm,String file){
        super(taskID,description);
        this.algorithm = algorithm;
        this.file=file;
    }
    public NatatieData getData() {
        return data;
    }
    @Override
    public void execute() {
        this.data=NatatieReader.readData(this.file);
        if(this.data==null){
            System.out.println("Nu ai date!");
            return;
        }
        algorithm.executeStrategy(this);
    }
    public String getFile(){
        return file;
    }
}
