package repo;

import models.ExamEntity2;
import utils.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamEntity2Repo implements Repository<Long,ExamEntity2>{
    private final Connection connection = DbConnection.getInstance().getConnection();
    @Override
    public Optional<ExamEntity2> findOne(Long id) {
        String sql = """
                SELECT *
                FROM table_name
                WHERE id = ?
                """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ExamEntity2 entity = entityFromResultSet(rs);
                    return Optional.of(entity);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<ExamEntity2> findAll() {
        String sql = """
                SELECT *
                FROM table_name
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql);ResultSet rs = ps.executeQuery()){
            List<ExamEntity2> entities = new ArrayList<>();
            while(rs.next()) {
                ExamEntity2 entity = entityFromResultSet(rs);
                entities.add(entity);
            }
            return entities;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(ExamEntity2 entity) {
        String sql = """
                INSERT INTO table_name (name)
                VALUES (?)
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, entity.getName());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM table_name
                WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(ExamEntity2 entity) {
        String sql = """
                UPDATE table_name
                SET name = ?
                WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setString(1, entity.getName());
            ps.setLong(2, entity.getId());
            ps.executeUpdate();
        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ExamEntity2 entityFromResultSet(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        Long id = rs.getLong("id");
        ExamEntity2 entity = new ExamEntity2(name);
        entity.setId(id);
        return entity;
    }
}
