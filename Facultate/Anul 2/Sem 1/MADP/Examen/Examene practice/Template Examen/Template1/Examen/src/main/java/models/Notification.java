package models;

public class Notification extends Entity<Long>{
    
    private final Long toId;
    private final String message;

    public Notification(Long toId, String message) {
        this.toId = toId;
        this.message = message;
    }
    public Long getToId() {
        return toId;
    }
    public String getMessage() {
        return message;
    }
}
