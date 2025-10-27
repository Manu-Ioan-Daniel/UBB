package taskuri;

import sortari.AbstractSorter;

public class SortingTask extends Task{
    private AbstractSorter sorter;
    private int[] vector;
    public SortingTask(String taskID, String descriere, AbstractSorter sorter, int[] vector) {
        super(taskID, descriere);
        this.sorter = sorter;
        this.vector = vector;
    }
    @Override
    public void execute(){
        sorter.sort(vector);
        System.out.print("Vector sortat: ");
        for(int num : vector){
            System.out.print(num + " ");
        }
    }
}



