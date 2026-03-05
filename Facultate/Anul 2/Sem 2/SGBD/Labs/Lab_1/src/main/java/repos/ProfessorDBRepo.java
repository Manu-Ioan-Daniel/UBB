package repos;

import models.Professor;
import utils.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProfessorDBRepo implements ProfessorRepository {
    @Override
    public Professor findOne(Long id) {
        return null;
    }

    @Override
    public Professor findByEmail(String email) {
        String sql = """
                SELECT * FROM profesori
                WHERE email = ?;
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return professorFromResultSet(rs);
            }else{
                return null;
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * Creeaza un profesor dintr un result set
     * @param rs result set ul din care se creeaza profesorul, trebuie sa contina coloanele id, name, age, email si materie_id
     * @return profesorul creat
     * @throws SQLException daca result set ul nu contine coloanele necesare sau daca valorile din coloane nu sunt de tipul corect
     */
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

        String sql = """
                INSERT INTO profesori(name,age,email,materie_id)
                VALUES(?,?,?,?)
                """;

        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)){

            ps.setString(1, professor.getName());
            ps.setInt(2, professor.getAge());
            ps.setString(3, professor.getEmail());
            ps.setLong(4, professor.getMaterieId());
            ps.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = """
                DELETE FROM profesori
                WHERE id = ?
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Professor professor) {
        String sql = """
                UPDATE profesori
                SET name = ?,age = ?,email = ?,materie_id = ?
                WHERE id = ?
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)){

            ps.setString(1, professor.getName());
            ps.setInt(2, professor.getAge());
            ps.setString(3, professor.getEmail());
            ps.setLong(4, professor.getMaterieId());
            ps.setLong(5, professor.getId());
            ps.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
