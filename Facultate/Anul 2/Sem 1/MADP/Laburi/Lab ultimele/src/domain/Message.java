package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private Long id;
    private final User from;
    private List<User> to;
    private String message;
    private final LocalDateTime date;
    Message(Long id, User from, List<User> to, String message, LocalDateTime date){
        this.id=id;
        this.from=from;
        this.to=to;
        this.message=message;
        this.date=date;
    }
    Message(User from, List<User> to, String message, LocalDateTime date) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.date = date;
    }
    public Long getId() {
        return id;
    }
    public User getFrom() {
        return from;
    }
    public List<User> getTo() {
        return to;
    }
    public String getMessage() {
        return message;
    }
}
