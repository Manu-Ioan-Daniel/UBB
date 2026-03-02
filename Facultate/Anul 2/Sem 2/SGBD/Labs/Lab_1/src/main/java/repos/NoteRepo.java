package repos;

import utils.DatabaseManager;
import utils.Tuple;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NoteRepo implements Repository<Tuple<Long,Long>, Long>{

    // id-ul este format din id-ul studentului si id-ul materiei
    @Override
    public Long findOne(Tuple<Long, Long> id) {
        String sql = """
                SELECT nota FROM note_studenti WHERE student_id = ? AND materie_id = ?
                """;
        try(PreparedStatement ps = DatabaseManager.getInstance().getConnection().prepareStatement(sql)) {
            ps.setLong(1, id.getFirst());
            ps.setLong(2, id.getSecond());
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return rs.getLong("nota");
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Long> findAll() {
        return List.of();
    }

    @Override
    public void save(Long aLong) {

    }

    @Override
    public void delete(Tuple<Long, Long> longLongTuple) {

    }

    @Override
    public void update(Long aLong) {

    }
}
