package teste;
import taskuri.MessageTask;
import java.time.LocalDateTime;
import taskuri.StrategyTaskRunner;
import utils.Strategy;
import taskuri.PrinterTaskRunner;
import taskuri.DelayTaskRunner;

public class Tester {
    MessageTask[] array;
    public Tester(){
        array = new MessageTask[5];
        array[0] = new MessageTask("1", "descriere1", "mesaj1", "from1", "to1",LocalDateTime.of(2024, 6, 20, 12, 0));
        array[1] = new MessageTask("2", "descriere2", "mesaj2", "from2", "to2",LocalDateTime.of(2020,2,15,6,7) );
        array[2] = new MessageTask("3", "descriere3", "mesaj3", "from3", "to3",LocalDateTime.of(2023,12,25,23,59) );
        array[3] = new MessageTask("4", "descriere4", "mesaj4", "from4", "to4",LocalDateTime.of(2021,1,1,1,1) );
        array[4] = new MessageTask("5", "descriere5", "mesaj5", "from5", "to5", LocalDateTime.of(2022,5,5,5,5));
    }
    public void messageTaskTester() {
        System.out.println("Testare MessageTask:");
        for (int i = 0; i < 5; i++) {
            System.out.println(array[i].toString());
        }

    }
    public void strategyTaskRunnerTester(){
        System.out.println("Testare StrategyTaskRunner:");
        StrategyTaskRunner runner=new StrategyTaskRunner(Strategy.FIFO);
        for(int i=0;i<5;i++){
            runner.addTask(array[i]);
        }
        System.out.println("Executare toate taskurile FIFO:");
        runner.executeAll();
        for(int i=0;i<5;i++) {
            runner.addTask(array[i]);
        }
        while(runner.hasTask()){
            runner.executeOneTask();
        }
        StrategyTaskRunner runner2=new StrategyTaskRunner(Strategy.LIFO);
        for(int i=0;i<5;i++){
            runner2.addTask(array[i]);
        }
        System.out.println("Executare toate taskurile LIFO:");
        runner2.executeAll();
        for(int i=0;i<5;i++) {
            runner2.addTask(array[i]);
        }
        while(runner2.hasTask()) {
            runner2.executeOneTask();
        }
    }
    public void printerTaskRunnerTester(){
        System.out.println("Testare PrinterTaskRunner:");
        StrategyTaskRunner runner= new StrategyTaskRunner(Strategy.FIFO);
        for(int i=0;i<5;i++){
            runner.addTask(array[i]);
        }
        runner.executeAll();
        PrinterTaskRunner printerRunner=new PrinterTaskRunner(runner);
        for(int i=0;i<5;i++){
            printerRunner.addTask(array[i]);
        }
        while(printerRunner.hasTask()){
            printerRunner.executeOneTask();
        }
    }
    public void delayTaskRunnerTester(){
        System.out.println("Testare DelayTaskRunner:");
        StrategyTaskRunner runner= new StrategyTaskRunner(Strategy.FIFO);
        for(int i=0;i<5;i++){
            runner.addTask(array[i]);
        }
        runner.executeAll();
        DelayTaskRunner delayRunner=new DelayTaskRunner(runner);
        for(int i=0;i<5;i++){
            delayRunner.addTask(array[i]);
        }
        while(delayRunner.hasTask()){
            delayRunner.executeOneTask();
        }
    }
}
