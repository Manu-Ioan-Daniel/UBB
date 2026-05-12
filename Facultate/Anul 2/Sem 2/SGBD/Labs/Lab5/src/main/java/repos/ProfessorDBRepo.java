package repos;

import models.Professor;
import utils.OldDBManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ProfessorDBRepo implements ProfessorRepository {
    @Override
    public Professor findOne(Long id) {
        String sql = "SELECT * FROM profesori WHERE id = ? AND is_deleted = false";
        try (PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return professorFromResultSet(rs);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Professor findByEmail(String email) {
        String sql = "SELECT * FROM profesori WHERE email = ? AND is_deleted = false";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
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
    public List<Professor> findPageByMaterieOffset(Long materieId, int pageNumber, int pageSize) {
        int offset = Math.max(0, pageNumber) * Math.max(1, pageSize);
        String sql = "SELECT * FROM profesori WHERE materie_id = ? AND is_deleted = false ORDER BY id LIMIT ? OFFSET ?";
        try (PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setLong(1, materieId);
            ps.setInt(2, pageSize);
            ps.setInt(3, offset);
            ResultSet rs = ps.executeQuery();
            List<Professor> profesori = new java.util.ArrayList<>();
            while (rs.next()) {
                profesori.add(professorFromResultSet(rs));
            }
            return profesori;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Professor> findPageByMaterieAfter(Long materieId, Long lastId, int pageSize) {
        String sqlWithLast = "SELECT * FROM profesori WHERE materie_id = ? AND is_deleted = false AND id > ? ORDER BY id LIMIT ?";
        String sqlNoLast = "SELECT * FROM profesori WHERE materie_id = ? AND is_deleted = false ORDER BY id LIMIT ?";
        try {
            PreparedStatement ps;
            if (lastId == null) {
                ps = OldDBManager.getInstance().getConnection().prepareStatement(sqlNoLast);
                ps.setLong(1, materieId);
                ps.setInt(2, pageSize);
            } else {
                ps = OldDBManager.getInstance().getConnection().prepareStatement(sqlWithLast);
                ps.setLong(1, materieId);
                ps.setLong(2, lastId);
                ps.setInt(3, pageSize);
            }
            try (ps) {
                ResultSet rs = ps.executeQuery();
                List<Professor> profesori = new java.util.ArrayList<>();
                while (rs.next()) {
                    profesori.add(professorFromResultSet(rs));
                }
                return profesori;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Professor> findAll() {
        String sql = "SELECT * FROM profesori WHERE is_deleted = false";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)) {
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

    @Override
    public List<Professor> findAllDeleted() {
        String sql = "SELECT * FROM profesori WHERE is_deleted = true ORDER BY deleted_at DESC";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)) {
            List<Professor> profesori = new java.util.ArrayList<>();
            var rs = ps.executeQuery();
            while(rs.next()){
                profesori.add(professorFromResultSet(rs));
            }
            return profesori;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Professor professorFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String email = rs.getString("email");
        Long materieId = rs.getLong("materie_id");
        String phone = null;
        try { phone = rs.getString("phone"); } catch (SQLException ignored) {}

        Professor p = new Professor(name, age, email, phone, materieId);
        p.setId(id);
        return p;
    }

    @Override
    public void save(Professor professor) {
        String sql = "INSERT INTO profesori(name,age,email,materie_id,phone,is_deleted) VALUES(?,?,?,?,?,false)";

        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setString(1, professor.getName());
            ps.setInt(2, professor.getAge());
            ps.setString(3, professor.getEmail());
            ps.setLong(4, professor.getMaterieId());
            ps.setString(5, professor.getPhone());
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        softDelete(id, "system");
    }

    @Override
    public void softDelete(Long id, String deletedBy) {
        String sql = "UPDATE profesori SET is_deleted = true, deleted_at = ?, deleted_by = ? WHERE id = ? AND is_deleted = false";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setTimestamp(1, Timestamp.valueOf(java.time.LocalDateTime.now()));
            ps.setString(2, (deletedBy == null || deletedBy.isBlank()) ? "system" : deletedBy);
            ps.setLong(3, id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void restore(Long id) {
        String sql = "UPDATE profesori SET is_deleted = false, deleted_at = null, deleted_by = null WHERE id = ?";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hardDelete(Long id) {
        String sql = "DELETE FROM profesori WHERE id = ?";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setLong(1, id);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Professor professor) {
        String sql = "UPDATE profesori SET name = ?,age = ?,email = ?,materie_id = ?,phone = ? WHERE id = ? AND is_deleted = false";
        try(PreparedStatement ps = OldDBManager.getInstance().getConnection().prepareStatement(sql)){
            ps.setString(1, professor.getName());
            ps.setInt(2, professor.getAge());
            ps.setString(3, professor.getEmail());
            ps.setLong(4, professor.getMaterieId());
            ps.setString(5, professor.getPhone());
            ps.setLong(6, professor.getId());
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
