package factory;
import container.*;
public class TaskContainerFactory implements Factory {
    private int capacity;
    static TaskContainerFactory instance=null;
    private TaskContainerFactory(){}
    @Override
    public Container createContainer(utils.Strategy strategy) {
        if(capacity<=0){
            System.out.println("Capacity not set or invalid");
            return null;
        }
        return switch (strategy) {
            case FIFO -> new QueueContainer(capacity);
            case LIFO -> new StackContainer(capacity);
            default -> {
                System.out.println("Invalid strategy");
                yield null;
            }
        };
    }
    public static TaskContainerFactory getInstance(){
        if(instance==null){
            instance=new TaskContainerFactory();
        }
        return instance;
    }
    public void setCapacity(int capacity){
        this.capacity=capacity;
    }
}
