package runner;

import taskrunner.TaskRunner;

public abstract class AbstractTaskRunner implements TaskRunner {

    private TaskRunner taskRunner;

    public AbstractTaskRunner(TaskRunner taskRunner){
        this.taskRunner = taskRunner;
    }

    public void executeOneTask() {
        taskRunner.executeOneTask();
    }
    public void executeAll() {
        taskRunner.executeAll();
    }
    public void addTask(model.Task task) {
        taskRunner.addTask(task);
    }

    public boolean hasTask() {
        return taskRunner.hasTask();
    }
}
