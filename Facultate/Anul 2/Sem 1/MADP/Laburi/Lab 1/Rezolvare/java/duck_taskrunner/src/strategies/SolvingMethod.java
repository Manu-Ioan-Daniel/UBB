package strategies;
import model.Task;

import java.io.IOException;

public interface SolvingMethod {
    void solve(Task t) throws IOException;
}