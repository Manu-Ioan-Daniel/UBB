package container;
import taskuri.Task;
public class QueueContainer extends AbstractContainer {
    private int end;
    private int front;
    public QueueContainer(int capacity){
        super(capacity);
        this.end=0;
        this.front=0;
    }
    @Override
    public void add(Task task){
        if(size<tasks.length){
            tasks[end]=task;
            end=(end+1)%tasks.length;
            size++;
        }else{
            System.out.println("Container plin");
        }
    }
    @Override
    public Task remove(){
        if(size>0){
            Task removed=tasks[front];
            tasks[front]=null;
            front=(front+1)%tasks.length;
            size--;
            return removed;
        }else{
            System.out.println("Container gol");
            return null;
        }
    }

}
