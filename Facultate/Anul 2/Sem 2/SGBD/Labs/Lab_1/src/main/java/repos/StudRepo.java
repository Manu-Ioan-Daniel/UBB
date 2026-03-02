package repos;

import models.Student;
import utils.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudRepo implements Repository<Long, Student>{
    @Override
    public Student findOne(Long id) {
        return null;
    }

    @Override
    public List<Student> findAll() {
        String sql = """
                SELECT * FROM studenti
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            List<Student> studenti = new java.util.ArrayList<>();
            while(rs.next()){
                Student s = studentFromResultSet(rs);
                studenti.add(s);
            }
            return studenti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Student studentFromResultSet(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        String nume = rs.getString("name");
        Integer age = rs.getInt("age");
        Student s = new Student(nume,age);
        s.setId(id);
        return s;
    }
    @Override
    public void save(Student student) {

    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void update(Student student) {

    }
}
