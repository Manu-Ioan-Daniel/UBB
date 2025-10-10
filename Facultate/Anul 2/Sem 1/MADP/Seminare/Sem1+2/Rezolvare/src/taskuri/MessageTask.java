package taskuri;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MessageTask extends Task {
    private String mesaj;
    private String from;
    private String to;
    private LocalDateTime date;
    public MessageTask(String taskID, String descriere, String mesaj, String from, String to, LocalDateTime date){
        super(taskID, descriere);
        this.mesaj=mesaj;
        this.from=from;
        this.to=to;
        this.date=date;
    }
    @Override
    public void execute(){
        System.out.println("Mesaj: "+mesaj+" in data de:"+DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(date));
    }
    @Override
    public String toString(){
        return "id="+getTaskID()+"|description="+getDescriere()+"|message="+mesaj+"|from="+from+"|to="+to+"|date="+ DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(date);
    }

}
