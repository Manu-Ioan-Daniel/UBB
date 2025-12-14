package domain;

import java.time.LocalDateTime;
import java.util.List;

public class Message {
    private Long id;
    private final Long fromId;
    private final List<Long> toIds;
    private String message;
    private final LocalDateTime date;

    public Message(Long id, Long fromId, List<Long> toIds, String message, LocalDateTime date){
        this.id = id;
        this.fromId = fromId;
        this.toIds = toIds;
        this.message = message;
        this.date = date;
    }

    public Message(Long fromId, List<Long> toIds, String message, LocalDateTime date) {
        this.fromId = fromId;
        this.toIds = toIds;
        this.message = message;
        this.date = date;
    }

    public Long getId() { return id; }
    public Long getFromId() { return fromId; }
    public List<Long> getToIds() { return toIds; }
    public String getMessage() { return message; }
    public LocalDateTime getDate() { return date; }
}
