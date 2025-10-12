package taskrunner;

import container.Container;
import factory.Strategy;
import factory.TaskContainerFactory;
import model.Task;

public class StrategyTaskRunner implements TaskRunner{

    private Container container;

    public StrategyTaskRunner(Strategy strategy) {
       this.container = TaskContainerFactory.getInstance().createContainer(strategy);
    }

    @Override
    public void executeOneTask() {
        if(!container.isEmpty()){
            container.remove().execute();
        }
    }

    @Override
    public void executeAll() {
        while (!container.isEmpty()){
            container.remove().execute();
        }
    }

    @Override
    public void addTask(Task task) {
        container.add(task);
    }

    @Override
    public boolean hasTask() {
        return !container.isEmpty();
    }
}
