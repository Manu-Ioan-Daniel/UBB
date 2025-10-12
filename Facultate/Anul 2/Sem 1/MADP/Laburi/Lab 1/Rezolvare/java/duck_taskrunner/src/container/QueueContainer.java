package container;

import model.Task;

public class QueueContainer implements Container {
    private Task[] tasks;
    private int size;
    private int front;
    private int back;
    public QueueContainer() {
        tasks = new Task[10];
        size = 0;
        front = 0;
        back = 0;
    }
    @Override
    public Task remove() {
        if(isEmpty()){
            System.out.println("No elements in the queue!");
            return null;
        }else{
            Task task=tasks[front];
            tasks[front]=null;
            front=(front+1)%(tasks.length);
            --size;
            return task;
        }
    }

    @Override
    public void add(Task task) {
        if(size==tasks.length){
            System.out.println("Nu poti adauga un task in coada.Coada este deja plina!");
            return;
        }
        tasks[back]=task;
        back=(back+1)%(tasks.length);
        ++size;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }
}
