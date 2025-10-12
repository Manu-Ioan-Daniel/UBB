package runner;

import model.Task;
import taskrunner.TaskRunner;

public class DelayTaskRunner extends AbstractTaskRunner{
    @Override
    public boolean hasTask() {
        return super.hasTask() ;
    }

    public DelayTaskRunner(TaskRunner taskRunner) {
        super(taskRunner);
    }
    public void executeAll(){
        while (hasTask()){
            executeOneTask();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
