package repo;

import enums.OrderStatus;
import models.Order;
import utils.DbConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderRepository implements Repository<Long, Order> {

    private final Connection connection = DbConnection.getInstance().getConnection();

    @Override
    public Optional<Order> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<Order> findAll() {

        String sql = """
                SELECT * FROM orders WHERE status = 'PLACED'
                """;
        List<Order> orders = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                long id = rs.getLong("id");
                Long tableId = rs.getLong("table_id");
                LocalDateTime date = rs.getTimestamp("date").toLocalDateTime();
                String statusStr = rs.getString("status");
                String sql2 = """
                        SELECT menu_item_id FROM order_items WHERE order_id = ?
                        """;
                List<Long> menuItems = new ArrayList<>();
                try(PreparedStatement ps2 = connection.prepareStatement(sql2)) {
                    ps2.setLong(1, id);
                    ResultSet rs2 = ps2.executeQuery();

                    while (rs2.next()) {
                        menuItems.add(rs2.getLong("menu_item_id"));
                    }
                }
                Order order = new Order(tableId, menuItems, date, OrderStatus.valueOf(statusStr));
                order.setId(id);
                orders.add(order);
            }

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return orders;
    }

    @Override
    public Optional<Order> save(Order entity) {
        String sql = """
                INSERT INTO orders (table_id, date, status)
                VALUES (?, ?, ?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            ps.setLong(1, entity.getTableId());
            ps.setTimestamp(2, Timestamp.valueOf(entity.getDate()));
            ps.setString(3, entity.getStatus().toString());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()){
                Long id = rs.getLong(1);
                entity.setId(id);
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        saveMenuItems(entity.getId(),entity.getMenuItems());
        return Optional.of(entity);
    }

    private void saveMenuItems(Long id, List<Long> menuItems) {
        String sql = """
                INSERT INTO order_items (order_id, menu_item_id)
                VALUES (?, ?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            for(Long menuItemId : menuItems){
                ps.setLong(1, id);
                ps.setLong(2, menuItemId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public Optional<Order> delete(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> update(Order entity) {
        return Optional.empty();
    }

    public List<Long> findMenuItemIdsByOrderId(Long orderId) {
        String sql = """
                SELECT menu_item_id FROM order_items WHERE order_id = ?
                """;
        List<Long> menuItemIds = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, orderId);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                menuItemIds.add(rs.getLong("menu_item_id"));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return menuItemIds;
    }
}
