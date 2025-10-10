package factory;
import utils.Strategy;
import container.Container;

public interface Factory {
    Container createContainer(Strategy strategy);
}
