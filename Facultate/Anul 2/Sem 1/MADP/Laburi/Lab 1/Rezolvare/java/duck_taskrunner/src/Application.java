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
        NatatieTask task=new NatatieTask("1","problema cu rate",new MyAlgorithm(SolvingStrategy.BSS),"natatie.in");
        StrategyTaskRunner runner=new StrategyTaskRunner(Strategy.LIFO);
        AbstractTaskRunner printRunner=new PrinterTaskRuner(runner);
        NatatieTask task1=new NatatieTask("2","problema cu rate",new MyAlgorithm(SolvingStrategy.BSS),"01-natatie.in");
        NatatieTask task2 = new NatatieTask("2", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "02-natatie.in");
        NatatieTask task3 = new NatatieTask("3", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "03-natatie.in");
        NatatieTask task4 = new NatatieTask("4", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "04-natatie.in");
        NatatieTask task5 = new NatatieTask("5", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "05-natatie.in");
        NatatieTask task11 = new NatatieTask("11", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "11-natatie.in");
        NatatieTask task12 = new NatatieTask("12", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "12-natatie.in");
        NatatieTask task13 = new NatatieTask("13", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "13-natatie.in");
        NatatieTask task14 = new NatatieTask("14", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "14-natatie.in");
        NatatieTask task15 = new NatatieTask("15", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "15-natatie.in");
        NatatieTask task16 = new NatatieTask("16", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "16-natatie.in");
        NatatieTask task17 = new NatatieTask("17", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "17-natatie.in");
        NatatieTask task18 = new NatatieTask("18", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "18-natatie.in");
        NatatieTask task19 = new NatatieTask("19", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "19-natatie.in");
        NatatieTask task20 = new NatatieTask("20", "problema cu rate", new MyAlgorithm(SolvingStrategy.BSS), "20-natatie.in");
        printRunner.addTask(task1);
        printRunner.addTask(task2);
        printRunner.addTask(task3);
        printRunner.addTask(task4);
        printRunner.addTask(task5);
        printRunner.addTask(task11);
        printRunner.addTask(task12);
        printRunner.addTask(task13);
        printRunner.addTask(task14);
        printRunner.addTask(task15);
        printRunner.addTask(task16);
        printRunner.executeAll();
        /*
            task1-expected time 42.977777778
            task2-expected time 25.178255372
            task3-expected time 3.785936693
            task4-expected time 3.389830508
            task5-expected time 2.738137100
            task11-expected time 74.85714285677264
            task12-expected time 156.6017699110994
            task13-expected time 66.07595870195688
            task14-expected time 346.49999999976365
            task15-expected time 17.852709191176764
         */

    }
}
