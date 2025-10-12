package model;
import algorithm.MyAlgorithm;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyNextTask extends Task {
    private final MyAlgorithm algorithm;
    private NatatieData data;
    public MyNextTask(String taskID, String description, MyAlgorithm algorithm){
        super(taskID,description);
        this.algorithm = algorithm;
    }
    private NatatieData readData(){
        File file = new File("src/data/natatie.in");
        try(Scanner scanner = new Scanner(file)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            Rata[] rate = new Rata[n];
            for(int i=0;i<n;i++){
               rate[i]=new Rata();
               rate[i].setViteza(scanner.nextInt());
            }
            for(int i=0;i<n;i++){
                rate[i].setRezistenta(scanner.nextInt());
            }
            int[] distante = new int[m];
            for(int i=0;i<m;i++){
                distante[i] = scanner.nextInt();
            }
            return new NatatieData(n,m,rate,distante);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            return null;
        }
    }
    public NatatieData getData() {
        return data;
    }

    @Override
    public void execute() {
        this.data=readData();
        if(this.data==null){
            System.out.println("No data available!");
            return;
        }
        algorithm.executeStrategy(this);
    }
}
