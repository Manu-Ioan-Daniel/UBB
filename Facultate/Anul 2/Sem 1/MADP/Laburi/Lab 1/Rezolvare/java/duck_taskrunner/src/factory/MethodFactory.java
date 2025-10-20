package factory;

import strategies.SolvingMethod;
import utils.enums.SolvingStrategy;

public interface MethodFactory {
    public SolvingMethod createMethod(SolvingStrategy strat);
}
