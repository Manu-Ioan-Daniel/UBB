package repos;

import models.Materie;
import utils.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaterieRepo implements Repository<Long, Materie>{

    @Override
    public Materie findOne(Long id) {
        return null;
    }

    @Override
    public List<Materie> findAll() {
        String sql = "SELECT * FROM materii";
        try(Connection connection = DatabaseManager.getInstance().getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Materie> materii = new ArrayList<>();

            while(resultSet.next()){
                Materie materie = createMaterieFromRS(resultSet);
                materii.add(materie);
            }
            return materii;
        }
        catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Materie materie) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void update(Materie materie) {

    }

    private Materie createMaterieFromRS(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        int credits = rs.getInt("credits");
        Materie materie = new Materie(name, credits);
        materie.setId(rs.getLong("id"));
        return materie;
    }
}
