package runner;

import model.Task;
import taskrunner.TaskRunner;
import utils.Constants;

import java.time.LocalDateTime;

public class PrinterTaskRuner extends AbstractTaskRunner{
    public PrinterTaskRuner(TaskRunner taskRunner) {
        super(taskRunner);
    }

    @Override
    public void executeOneTask() {
        super.executeOneTask();
        System.out.println("Task was executed on:" + LocalDateTime.now().format(Constants.DATE_TIME_FORMAT));
    }

    @Override
    public void executeAll() {
        while(hasTask()){
            executeOneTask();
        }
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
    }

    @Override
    public boolean hasTask() {
        return super.hasTask();
    }
}
