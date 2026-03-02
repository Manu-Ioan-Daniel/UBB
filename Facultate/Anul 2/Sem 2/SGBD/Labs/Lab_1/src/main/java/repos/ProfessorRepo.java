package repos;

import models.Professor;
import utils.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProfessorRepo implements Repository<Long, Professor> {
    @Override
    public Professor findOne(Long id) {
        return null;
    }

    @Override
    public List<Professor> findAll() {
        String sql = """
                SELECT * FROM profesori
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            List<Professor> profesori = new java.util.ArrayList<>();
            var rs = ps.executeQuery();
            while(rs.next()){
                Professor p = professorFromResultSet(rs);
                profesori.add(p);
            }
            return profesori;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Professor professorFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String email = rs.getString("email");
        Long materieId = rs.getLong("materie_id");
        Professor p = new Professor(name, age, email, materieId);
        p.setId(id);
        return p;
    }

    @Override
    public void save(Professor professor) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Professor professor) {

    }
}
