import algorithm.MyAlgorithm;
import model.MyNextTask;
import strategies.BinarySearchStrategy;

public class Application {
    public static void main(String[] args) {
        MyNextTask task= new MyNextTask("1","problema cu rate",new MyAlgorithm(new BinarySearchStrategy()));
        task.execute();
    }
}
