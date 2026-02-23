package repo;


import models.Admin;
import models.NormalUser;
import models.User;
import utils.DbConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserRepo{

    public User findByUsername(String username){
        String sql = """
                SELECT * FROM users WHERE username = ?;
                """;
        try(PreparedStatement ps = DbConnection.getInstance().getConnection().prepareStatement(sql)){
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String password = rs.getString("password");
                String role = rs.getString("role");
                if(role.equals("admin")){
                    return new Admin(username,password);
                } else {
                    return new NormalUser(username,password);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}
