package repo;

import models.MenuItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItemRepository extends AbstractDbRepository<MenuItem> {

    @Override
    public MenuItem createEntity(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String category = rs.getString("category");
        String item = rs.getString("item");
        float price = rs.getFloat("price");
        String currency = rs.getString("currency");
        MenuItem menuItem = new MenuItem(category,item, price, currency);
        menuItem.setId(id);
        return menuItem;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        String sql = """
                SELECT * FROM menu WHERE id = ?
                """;
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setLong(1, id);
        return ps;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String query = "SELECT * FROM menu";
        return connection.prepareStatement(query);
    }

    @Override
    public PreparedStatement saveStatement(MenuItem entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(MenuItem entity) throws SQLException {
        return null;
    }
}
