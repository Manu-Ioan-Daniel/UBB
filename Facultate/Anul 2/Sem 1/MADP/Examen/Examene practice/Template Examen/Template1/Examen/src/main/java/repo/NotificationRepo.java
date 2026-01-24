package repo;

import models.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NotificationRepo extends AbstractDbRepository<Notification> implements Repository<Long, Notification>{

    @Override
    public Notification createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long toId = rs.getLong("to_id");
        String message = rs.getString("message");
        Notification notification = new Notification(toId, message);
        notification.setId(id);
        return notification;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        String query = "SELECT * FROM notifications WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String query = "SELECT * FROM notifications";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(Notification entity) throws SQLException {
        String query = "INSERT INTO notifications (to_id, message) VALUES (?, ?)";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, entity.getToId());
        ps.setString(2, entity.getMessage());
        return ps;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        String query = "DELETE FROM notifications WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement updateStatement(Notification entity) throws SQLException {
        String query = "UPDATE notifications SET to_id = ?, message = ? WHERE id = ?";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setLong(1, entity.getToId());
        ps.setString(2, entity.getMessage());
        ps.setLong(3, entity.getId());
        return ps;
    }

    public List<Notification> findNotifications(Long toId) {
        String query = "SELECT * FROM notifications WHERE to_id = ?";
        List<Notification> entities = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setLong(1, toId);
            try( ResultSet rs = ps.executeQuery()) {

                while(rs.next()) {
                    Notification notification = createEntity(rs);
                    entities.add(notification);
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding notifications for toId: " + toId, e);
        }

        return entities;
    }
}