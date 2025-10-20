import algorithm.MyAlgorithm;
import factory.Strategy;
import factory.TaskMethodFactory;
import model.NatatieTask;
import runner.AbstractTaskRunner;
import runner.DelayTaskRunner;
import runner.PrinterTaskRuner;
import strategies.SolvingMethod;
import taskrunner.StrategyTaskRunner;
import utils.enums.SolvingStrategy;

public class Application {
    public static void main(String[] args) {
        NatatieTask task=new NatatieTask("1","problema cu rate",new MyAlgorithm(SolvingStrategy.BSS));
        StrategyTaskRunner runner=new StrategyTaskRunner(Strategy.LIFO);
        AbstractTaskRunner printRunner=new PrinterTaskRuner(runner);
        printRunner.addTask(task);
        printRunner.executeOneTask();
        AbstractTaskRunner delayRunner=new DelayTaskRunner(runner);
        delayRunner.addTask(task);
        delayRunner.addTask(task);
        delayRunner.addTask(task);
        delayRunner.executeAll();

    }
}
