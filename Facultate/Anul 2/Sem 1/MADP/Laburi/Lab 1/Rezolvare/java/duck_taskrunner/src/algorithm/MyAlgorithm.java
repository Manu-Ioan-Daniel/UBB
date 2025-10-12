package algorithm;
import model.Task;
import strategies.SolvingStrategy;

public class MyAlgorithm {
    SolvingStrategy strategy;
    public MyAlgorithm(SolvingStrategy strategy){
        this.strategy = strategy;
    }
    public void executeStrategy(Task t) {
        strategy.solve(t);
    }
}
