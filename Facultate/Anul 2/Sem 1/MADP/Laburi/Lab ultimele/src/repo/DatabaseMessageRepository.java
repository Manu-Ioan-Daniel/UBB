package repo;
import domain.Message;
import domain.ReplyMessage;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseMessageRepository {
    private final String url;

    public DatabaseMessageRepository(String url) {
        this.url = url;
    }

    public void save(Message message) {
        String sql = """
            INSERT INTO messages(from_id, message, date, reply_to)
            VALUES (?, ?, ?, ?)
            RETURNING id
        """;

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, message.getFromId());
            ps.setString(2, message.getMessage());
            ps.setTimestamp(3, Timestamp.valueOf(message.getDate()));

            if (message instanceof ReplyMessage rm) {
                ps.setLong(4, rm.getReplyToId());
            } else {
                ps.setNull(4, Types.BIGINT);
            }

            ResultSet rs = ps.executeQuery();
            rs.next();
            long messageId = rs.getLong(1);

            String insertTo = "INSERT INTO message_recipients(message_id, user_id) VALUES (?, ?)";
            try (PreparedStatement psTo = con.prepareStatement(insertTo)) {
                for (Long toId : message.getToIds()) {
                    psTo.setLong(1, messageId);
                    psTo.setLong(2, toId);
                    psTo.addBatch();
                }
                psTo.executeBatch();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Message> getConversation(Long userId1, Long userId2) {
        String sql = """
        SELECT m.id, m.from_id, m.message, m.date, m.reply_to,
               ARRAY(SELECT user_id FROM message_recipients WHERE message_id = m.id) AS to_ids
        FROM messages m
        WHERE (m.from_id = ? AND ? = ANY (SELECT user_id FROM message_recipients WHERE message_id = m.id))
           OR (m.from_id = ? AND ? = ANY (SELECT user_id FROM message_recipients WHERE message_id = m.id))
        ORDER BY m.date
    """;

        List<Message> result = new ArrayList<>();

        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(sql)) {


            ps.setLong(1, userId1);
            ps.setLong(2, userId2);
            ps.setLong(3, userId2);
            ps.setLong(4, userId1);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("id");
                long fromId = rs.getLong("from_id");
                String text = rs.getString("message");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                Long replyToId = rs.getObject("reply_to", Long.class);

                Array toArray = rs.getArray("to_ids");
                List<Long> toIds = new ArrayList<>();
                if (toArray != null) {
                    Long[] arr = (Long[]) toArray.getArray();
                    toIds.addAll(Arrays.asList(arr));
                }

                if (replyToId != null) {
                    result.add(new ReplyMessage(id, fromId, toIds, text, date, replyToId));
                } else {
                    result.add(new Message(id, fromId, toIds, text, date));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    public Message findMessageById(Long messageId) {
        String sql = "SELECT * FROM messages WHERE id=?";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, messageId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                long fromId = rs.getLong("from_id");
                String text = rs.getString("message");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                Long replyToId = rs.getObject("reply_to", Long.class);

                Array toArray = rs.getArray("to_ids");
                List<Long> toIds = new ArrayList<>();
                if (toArray != null) {
                    toIds.addAll(Arrays.asList((Long [])toArray.getArray()));
                }

                if (replyToId != null) {
                    return new ReplyMessage(id, fromId, toIds, text, date, replyToId);
                } else {
                    return new Message(id, fromId, toIds, text, date);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
