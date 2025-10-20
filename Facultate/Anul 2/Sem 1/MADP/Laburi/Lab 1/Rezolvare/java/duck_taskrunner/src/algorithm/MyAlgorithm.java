package algorithm;
import factory.TaskMethodFactory;
import model.Task;
import strategies.SolvingMethod;
import utils.enums.SolvingStrategy;

public class MyAlgorithm {
    private SolvingMethod method;
    public MyAlgorithm(SolvingStrategy strat){
        this.method=TaskMethodFactory.getInstance().createMethod(strat);
    }
    public void executeStrategy(Task t) {
        method.solve(t);
    }
}
