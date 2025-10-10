package container;
import taskuri.Task;

public class StackContainer extends AbstractContainer {
    private int top;
    public StackContainer(int size){
        super(size);
        this.top=0;
    }
    @Override
    public void add(Task task){
        if(top<tasks.length) {
            tasks[top] = task;
            top++;
            size++;
        }else{
            System.out.println("Container plin");
        }
    }
    @Override
    public Task remove(){
        if(top>0){
            Task removed=tasks[--top];
            tasks[top]=null;
            size--;
            return removed;
        }else{
            System.out.println("Container gol");
            return null;
        }
    }
}
