package taskuri;
import java.time.LocalTime;
public class PrinterTaskRunner extends AbstractTaskRunner{
    public PrinterTaskRunner(TaskRunner taskRunner){
        super(taskRunner);
    }
    @Override
    public void executeOneTask(){
        System.out.println("Incep executia unei task la ora: "+LocalTime.now().getHour());
        super.executeOneTask();
        System.out.println("S-a terminat executia unei task.");
    }
}
