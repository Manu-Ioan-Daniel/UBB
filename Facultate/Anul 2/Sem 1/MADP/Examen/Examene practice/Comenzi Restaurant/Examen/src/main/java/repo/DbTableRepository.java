package repo;

import models.Table;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbTableRepository extends AbstractDbRepository<Table>{

    @Override
    public Table createEntity(ResultSet rs) throws SQLException {
        Table table = new Table();
        table.setId(rs.getLong("id"));
        return table;
    }

    @Override
    public PreparedStatement findOneStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement findAllStatement() throws SQLException {
        String sql = "SELECT * FROM tables";
        return connection.prepareStatement(sql);
    }

    @Override
    public PreparedStatement saveStatement(Table entity) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement deleteStatement(Long id) throws SQLException {
        return null;
    }

    @Override
    public PreparedStatement updateStatement(Table entity) throws SQLException {
        return null;
    }
}
