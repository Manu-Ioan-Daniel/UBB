package domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private final Message replyTo;
    public ReplyMessage(Long id, User from, List<User> to, String message, LocalDateTime date, Message replyTo) {
        super(id, from, to, message, date);
        this.replyTo=replyTo;
    }
    public ReplyMessage(User from, List<User> to, String message, LocalDateTime date, Message replyTo) {
        super(from, to, message, date);
        this.replyTo=replyTo;
    }
    public Message getReplyTo() {
        return replyTo;
    }
}
