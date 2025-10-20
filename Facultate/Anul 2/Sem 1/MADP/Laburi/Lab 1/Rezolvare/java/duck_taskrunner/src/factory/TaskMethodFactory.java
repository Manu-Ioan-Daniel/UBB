package factory;

import strategies.BinarySearchMethod;
import strategies.SolvingMethod;
import utils.enums.SolvingStrategy;

public class TaskMethodFactory implements MethodFactory {
    private static TaskMethodFactory instance = null;
    private TaskMethodFactory(){}
    public static TaskMethodFactory getInstance() {
        if (instance == null) {
            instance = new TaskMethodFactory();
        }
        return instance;
    }
    @Override
    public SolvingMethod createMethod(SolvingStrategy strat){
        if(strat==SolvingStrategy.BSS){
            return new BinarySearchMethod();
        }
        return null;
    }
}
