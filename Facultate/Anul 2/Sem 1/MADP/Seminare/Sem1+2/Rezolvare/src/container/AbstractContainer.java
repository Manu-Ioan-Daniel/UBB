package container;

import taskuri.Task;

public abstract class AbstractContainer implements Container{
    protected Task[] tasks;
    protected int size;
    public AbstractContainer(int capacity){
        tasks = new Task[capacity];
        this.size=0;
    }
    @Override
    public int size(){
        return size;
    }
    @Override
    public boolean isEmpty(){
        return size==0;
    }
    public abstract void add(Task task);
    public abstract Task remove();
}
