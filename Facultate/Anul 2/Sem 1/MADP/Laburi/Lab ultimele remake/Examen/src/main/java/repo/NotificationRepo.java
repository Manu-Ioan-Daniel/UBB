package repo;

import models.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class NotificationRepo implements Repository<Long, Notification>{

    private final Connection connection = utils.DbConnection.getInstance().getConnection();

    @Override
    public Optional<Notification> findOne(Long id) {
        String sql = """
                    SELECT *
                    FROM notifications
                    WHERE id = ?
                    """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return Optional.of(notificationFromResultSet(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    private Notification notificationFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong(1);
        Long toId = rs.getLong(2);
        String message = rs.getString(3);
        Notification notification = new Notification(toId, message);
        notification.setId(id);
        return notification;
    }

    @Override
    public List<Notification> findAll() {
        String sql = """
                SELECT *
                from notifications
                """;
        List<Notification> notifications = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql);ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                notifications.add(notificationFromResultSet(rs));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return notifications;
    }

    public List<Notification> findNotifications(Long toId){
        String sql = """
                SELECT *
                FROM notifications
                WHERE to_id = ?
        """;
        List<Notification> notifications = new ArrayList<>();
        try(PreparedStatement ps =connection.prepareStatement(sql)){
            ps.setLong(1, toId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    notifications.add(notificationFromResultSet(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return notifications;
    }

    @Override
    public void save(Notification entity) {
        String sql = """
                INSERT INTO notifications(to_id,message)
                VALUES (?, ?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,entity.getToId());
            ps.setString(2, entity.getMessage());
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM notifications
                WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1,id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Notification entity) {

    }
}