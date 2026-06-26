package template.template.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RaspunsRepository {

    private static final Logger logger = LogManager.getLogger(RaspunsRepository.class);

    private final String url = "jdbc:mysql://localhost:3306/TariOraseMPP";
    private final String username = "root";
    private final String password = "";

    public Optional<Integer> gasestePuncte(String categorie, String raspuns) {
        String sql = "SELECT puncte FROM raspunsuri WHERE LOWER(categorie) = ? AND LOWER(raspuns) = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie.toLowerCase().trim());
            stmt.setString(2, raspuns.toLowerCase().trim());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(rs.getInt("puncte"));
                }
            }
        } catch (SQLException e) {
            logger.error("Eroare la cautare puncte in DB: " + e.getMessage());
        }
        return Optional.empty();
    }

    public void adaugaRaspuns(String categorie, String raspuns, int puncte) throws SQLException {
        String sql = "INSERT INTO raspunsuri (categorie, raspuns, puncte) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categorie);
            stmt.setString(2, raspuns);
            stmt.setInt(3, puncte);
            stmt.executeUpdate();
        }
    }

    public int getMForGame(long idJoc) throws SQLException {
        String sql = "SELECT m FROM jocuri WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idJoc);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("m");
                }
            }
        }
        return 0;
    }

    public List<String> getWinnersForGame(long idJoc, int minRunde) throws SQLException {
        String sql = "SELECT porecla FROM joc_jucatori WHERE id_joc = ? AND runde_castigate >= ?";
        List<String> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idJoc);
            stmt.setInt(2, minRunde);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getString("porecla"));
                }
            }
        }
        return list;
    }

    public List<Integer> getPuncteRundeForPlayer(long idJoc, String porecla) throws SQLException {
        String sql = "SELECT runda, SUM(puncte) as puncte_runda FROM istoric_raspunsuri WHERE id_joc = ? AND porecla = ? GROUP BY runda ORDER BY runda";
        List<Integer> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idJoc);
            stmt.setString(2, porecla);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("puncte_runda"));
                }
            }
        }
        return list;
    }

    public List<Map<String, Object>> getRaspunsuriForPlayer(long idJoc, String porecla) throws SQLException {
        String sql = "SELECT runda, categorie, raspuns, puncte FROM istoric_raspunsuri WHERE id_joc = ? AND porecla = ? ORDER BY runda, categorie";
        List<Map<String, Object>> list = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idJoc);
            stmt.setString(2, porecla);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("runda", rs.getInt("runda"));
                    map.put("categorie", rs.getString("categorie"));
                    map.put("raspuns", rs.getString("raspuns"));
                    map.put("puncte", rs.getInt("puncte"));
                    list.add(map);
                }
            }
        }
        return list;
    }
}
