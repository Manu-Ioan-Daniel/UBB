package models;

import utils.database.DbConnection;
import utils.passwordUtils.PasswordHasher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    private final Connection connection;
    public UserModel() throws SQLException {
        connection= DbConnection.getInstance().getConnection();
    }

    public boolean validLogin(String username,String password){
        String sql = """
                SELECT password
                FROM users
                WHERE username = ?
                """;
        String storedHash;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){

                storedHash=rs.getString("password");
                return PasswordHasher.verifyPassword(password, storedHash);

            }else
                return false;

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

}
