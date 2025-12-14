package domain;

import java.time.LocalDateTime;
import java.util.List;

public class ReplyMessage extends Message {
    private final Long replyToId;

    public ReplyMessage(Long id, Long fromId, List<Long> toIds, String message, LocalDateTime date, Long replyToId) {
        super(id, fromId, toIds, message, date);
        this.replyToId = replyToId;
    }

    public ReplyMessage(Long fromId, List<Long> toIds, String message, LocalDateTime date, Long replyToId) {
        super(fromId, toIds, message, date);
        this.replyToId = replyToId;
    }

    public Long getReplyToId() { return replyToId; }
}
