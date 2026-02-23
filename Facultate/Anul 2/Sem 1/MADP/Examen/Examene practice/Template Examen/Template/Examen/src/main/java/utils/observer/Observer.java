package utils.observer;
import enums.ChangeEvent;

public interface Observer {
    void update(ChangeEvent event);
}
