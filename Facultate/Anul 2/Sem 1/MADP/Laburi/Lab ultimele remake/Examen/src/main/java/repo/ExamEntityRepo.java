package repo;

import models.ExamEntity;
import utils.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamEntityRepo implements Repository<Long, ExamEntity>{
    private final Connection connection = DbConnection.getInstance().getConnection();
    @Override
    public Optional<ExamEntity> findOne(Long id) {
        String sql = """
                SELECT *
                FROM table_name
                WHERE id = ?
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.setLong(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()) {
                    ExamEntity entity = entityFromResultSet(rs);
                    return Optional.of(entity);
                }
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<ExamEntity> findAll() {
        String sql = """
                SELECT *
                FROM table_name
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql);ResultSet rs = ps.executeQuery()){
            List<ExamEntity> entities = new ArrayList<>();
            while(rs.next()) {
                ExamEntity entity = entityFromResultSet(rs);
                entities.add(entity);
            }
            return entities;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public void save(ExamEntity entity) {

        String sql = """
                INSERT INTO table_name
                VALUES ()
                """;
        try(PreparedStatement ps = connection.prepareStatement(sql)){
            ps.executeUpdate();
        }
        catch(SQLException e){
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
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }

    }


    @Override
    public void update(ExamEntity entity) {

    }

    private ExamEntity entityFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        ExamEntity entity = new ExamEntity();
        entity.setId(id);
        return entity;
    }
}
