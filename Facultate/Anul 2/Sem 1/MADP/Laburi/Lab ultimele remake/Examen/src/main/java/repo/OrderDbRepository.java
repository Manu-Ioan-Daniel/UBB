package repo;

import enums.OrderStatus;
import models.Order;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderDbRepository extends AbstractDbRepository<Order> {


    @Override
    public Order createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long driverId = rs.getLong("driver_id");
        if(rs.wasNull()){
            driverId = null;
        }
        OrderStatus orderStatus = OrderStatus.valueOf(rs.getString("status"));
        LocalDateTime startDate = rs.getTimestamp("start_date").toLocalDateTime();
        Timestamp endTimestamp = rs.getTimestamp("end_date");
        LocalDateTime endDate;
        if(rs.wasNull()){
            endDate = null;
        }else{
            endDate = endTimestamp.toLocalDateTime();
        }
        String pickupAdress = rs.getString("pickup_adress");
        String destinationAddress = rs.getString("destination_adress");
        String clientName = rs.getString("client_name");
        Order order = new Order(driverId, orderStatus, startDate, endDate, pickupAdress, destinationAddress, clientName);
        order.setId(id);
        return order;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        String sql = """
                SELECT * FROM orders WHERE id = ?
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String sql = """
                SELECT * FROM orders
                """;
        return connection.prepareStatement(sql);
    }

    @Override
    public PreparedStatement saveStatement(Order entity) throws SQLException {
        String sql = """
                INSERT INTO orders (driver_id, status, start_date, end_date, pickup_adress, destination_adress, client_name)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setNull(1, Types.INTEGER);
        ps.setString(2, entity.getStatus().toString());
        ps.setTimestamp(3, Timestamp.valueOf(entity.getStartDate()));
        ps.setNull(4, Types.TIMESTAMP);
        ps.setString(5, entity.getPickupAdress());
        ps.setString(6, entity.getDestinationAdress());
        ps.setString(7, entity.getClientName());
        return ps;

    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Order entity) throws SQLException {
        String sql = """
                UPDATE orders SET driver_id = ?,status = ?, end_date = ? WHERE id = ?
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, entity.getDriverId());
        ps.setString(2, entity.getStatus().toString());
        ps.setTimestamp(3, java.sql.Timestamp.valueOf(entity.getEndDate()));
        ps.setLong(4, entity.getId());
        return ps;
    }

    public List<Order> findActiveOrdersByDriverId(Long driverId) {
        String sql = """
                SELECT * FROM orders WHERE driver_id = ? AND status = 'IN_PROGRESS'
                """;
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, driverId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    orders.add(createEntity(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return orders;
    }

    public List<Order> findDriverOrders(Long driverId){
        String sql = """
                SELECT * FROM orders WHERE driver_id = ?
        """;
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, driverId);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()){
                    orders.add(createEntity(rs));
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return orders;
    }

    public Order getLastOrder() {
        String sql = """
                SELECT*
                FROM orders
                ORDER BY id DESC
                LIMIT 1;
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return createEntity(rs);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return null;
    }
}
