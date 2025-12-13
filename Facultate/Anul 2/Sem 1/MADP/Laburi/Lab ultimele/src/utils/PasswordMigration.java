package utils;

import java.sql.*;

public class PasswordMigration {
    public static void main(String[] args) {
        String databaseURL = "jdbc:postgresql://localhost:5432/MAP?user=postgres&password=123";

        try (Connection connection = DriverManager.getConnection(databaseURL)) {
            String selectSQL = "SELECT userId, userPassword FROM users";
            String updateSQL = "UPDATE users SET userPassword = ? WHERE userId = ?";

            try (PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
                 PreparedStatement updateStmt = connection.prepareStatement(updateSQL)) {

                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    long userId = rs.getLong("userId");
                    String plainPassword = rs.getString("userPassword");

                    String hashedPassword = PasswordUtils.hashPassword(plainPassword);

                    updateStmt.setString(1, hashedPassword);
                    updateStmt.setLong(2, userId);
                    updateStmt.executeUpdate();
                }
            }

            System.out.println("All passwords migrated successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}