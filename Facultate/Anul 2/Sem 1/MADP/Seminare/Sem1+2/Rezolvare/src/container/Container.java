package container;
import taskuri.Task;
public interface Container {
    Task remove();
    void add(Task task);
    int size();
    boolean isEmpty();
}
