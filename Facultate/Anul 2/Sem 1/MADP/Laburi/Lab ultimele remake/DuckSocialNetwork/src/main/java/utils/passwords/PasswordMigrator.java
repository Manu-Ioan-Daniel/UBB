package utils.passwords;

import utils.database.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswordMigrator {
    public static void main(String[] args) throws Exception {
         Connection connection = DbConnection.getInstance().getConnection();
         String sql = """
                 SELECT id,password
                 FROM users
                 """;
         PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery();
         while(rs.next()){
             long id = rs.getLong("id");
             String oldPass = rs.getString("password");
             byte[] salt = PasswordHasher.generateSalt();
             byte[] hashedPassword = PasswordHasher.hashPassword(oldPass,salt);
             String hashedPasswordString = PasswordHasher.byteToBase64(salt)+":"+PasswordHasher.byteToBase64(hashedPassword);
             String sql2 = """
                     UPDATE users
                     SET password = ?
                     WHERE id = ?
                     """;
             PreparedStatement ps2 = connection.prepareStatement(sql2);
             ps2.setString(1,hashedPasswordString);
             ps2.setLong(2,id);
             ps2.executeUpdate();
         }
    }
}
