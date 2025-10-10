package taskuri;
import container.Container;
import utils.Strategy;
import factory.TaskContainerFactory;
public class StrategyTaskRunner implements TaskRunner {
    private Container container;
    public StrategyTaskRunner(Strategy strategy){
        TaskContainerFactory.getInstance().setCapacity(50);
        container=TaskContainerFactory.getInstance().createContainer(strategy);
    }
    @Override
    public void addTask(Task task) {
        container.add(task);
    }
    @Override
    public void executeOneTask(){
        if(!container.isEmpty()){
            Task task=container.remove();
            task.execute();
        } else {
            System.out.println("No tasks to execute");
        }
    }
    @Override
    public void executeAll(){
        while(!container.isEmpty()){
            executeOneTask();
        }
    }
    @Override
    public boolean hasTask(){
        return !container.isEmpty();
    }
}
