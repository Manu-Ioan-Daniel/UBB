package template.template.utils;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import template.template.domain.Categorie;
import template.template.domain.Jucator;
import template.template.repository.CategorieRepository;
import template.template.repository.JucatorRepository;

import java.sql.*;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private static final Logger logger = LogManager.getLogger(DatabaseInitializer.class);

    private final JucatorRepository jucatorRepository;
    private final CategorieRepository categorieRepository;

    private final String url = "jdbc:mysql://localhost:3306/TariOraseMPP";
    private final String username = "root";
    private final String password = "";

    @Override
    public void run(String... args) throws Exception {
        logger.info("Initializare baza de date...");

        try {
            if (jucatorRepository.findAll().isEmpty()) {
                jucatorRepository.save(new Jucator("Daniel", 20));
                jucatorRepository.save(new Jucator("Deny", 21));
                jucatorRepository.save(new Jucator("Maria", 22));
                jucatorRepository.save(new Jucator("Ionut", 19));
                jucatorRepository.save(new Jucator("Elena", 23));
            }

            String[] categoriiMacheta = {"Tari", "Orase", "Ape", "Munti", "Plante", "Animale", "Nume", "Masini"};
            for (String cat : categoriiMacheta) {
                if (categorieRepository.findByNume(cat).isEmpty()) {
                    categorieRepository.save(new Categorie(cat));
                }
            }

            try (Connection conn = DriverManager.getConnection(url, username, password);
                 Statement stmt = conn.createStatement()) {
                stmt.execute("CREATE TABLE IF NOT EXISTS raspunsuri (" +
                        "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
                        "categorie VARCHAR(255)," +
                        "raspuns VARCHAR(255)," +
                        "puncte INT" +
                        ")");

                boolean tableEmpty = true;
                try (ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM raspunsuri")) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        tableEmpty = false;
                    }
                }

                if (tableEmpty) {
                    // Tari
                    insertRaspuns(conn, "Tari", "Austria", 10);
                    insertRaspuns(conn, "Tari", "Anglia", 10);
                    insertRaspuns(conn, "Tari", "Belgia", 10);
                    insertRaspuns(conn, "Tari", "Bulgaria", 10);
                    insertRaspuns(conn, "Tari", "Cehia", 10);
                    insertRaspuns(conn, "Tari", "Canada", 10);
                    insertRaspuns(conn, "Tari", "Danemarca", 10);
                    insertRaspuns(conn, "Tari", "Dominica", 10);
                    insertRaspuns(conn, "Tari", "Egipt", 10);
                    insertRaspuns(conn, "Tari", "Estonia", 10);

                    // Orase
                    insertRaspuns(conn, "Orase", "Arad", 10);
                    insertRaspuns(conn, "Orase", "Atena", 10);
                    insertRaspuns(conn, "Orase", "Brasov", 10);
                    insertRaspuns(conn, "Orase", "Bucuresti", 10);
                    insertRaspuns(conn, "Orase", "Cluj", 10);
                    insertRaspuns(conn, "Orase", "Constanta", 10);
                    insertRaspuns(conn, "Orase", "Deva", 10);
                    insertRaspuns(conn, "Orase", "Dublin", 10);
                    insertRaspuns(conn, "Orase", "Eforie", 10);
                    insertRaspuns(conn, "Orase", "Essen", 10);

                    // Ape
                    insertRaspuns(conn, "Ape", "Arges", 10);
                    insertRaspuns(conn, "Ape", "Amazon", 10);
                    insertRaspuns(conn, "Ape", "Bega", 10);
                    insertRaspuns(conn, "Ape", "Bistrita", 10);
                    insertRaspuns(conn, "Ape", "Cerna", 10);
                    insertRaspuns(conn, "Ape", "Cris", 10);
                    insertRaspuns(conn, "Ape", "Dunarea", 10);
                    insertRaspuns(conn, "Ape", "Dambovita", 10);
                    insertRaspuns(conn, "Ape", "Elba", 10);
                    insertRaspuns(conn, "Ape", "Euphrat", 10);

                    // Munti
                    insertRaspuns(conn, "Munti", "Alpi", 10);
                    insertRaspuns(conn, "Munti", "Apuseni", 10);
                    insertRaspuns(conn, "Munti", "Bucegi", 10);
                    insertRaspuns(conn, "Munti", "Bila", 10);
                    insertRaspuns(conn, "Munti", "Carpati", 10);
                    insertRaspuns(conn, "Munti", "Ceahlau", 10);
                    insertRaspuns(conn, "Munti", "Dolomiti", 10);
                    insertRaspuns(conn, "Munti", "Everest", 10);

                    // Plante
                    insertRaspuns(conn, "Plante", "Aloe", 10);
                    insertRaspuns(conn, "Plante", "Ananas", 10);
                    insertRaspuns(conn, "Plante", "Bambus", 10);
                    insertRaspuns(conn, "Plante", "Busuioc", 10);
                    insertRaspuns(conn, "Plante", "Cirese", 10);
                    insertRaspuns(conn, "Plante", "Crizantema", 10);
                    insertRaspuns(conn, "Plante", "Dalie", 10);
                    insertRaspuns(conn, "Plante", "Dovleac", 10);
                    insertRaspuns(conn, "Plante", "Eucalipt", 10);

                    // Animale
                    insertRaspuns(conn, "Animale", "Aligator", 10);
                    insertRaspuns(conn, "Animale", "Arici", 10);
                    insertRaspuns(conn, "Animale", "Bursuc", 10);
                    insertRaspuns(conn, "Animale", "Bizon", 10);
                    insertRaspuns(conn, "Animale", "Caine", 10);
                    insertRaspuns(conn, "Animale", "Cal", 10);
                    insertRaspuns(conn, "Animale", "Delfin", 10);
                    insertRaspuns(conn, "Animale", "Dinozaur", 10);
                    insertRaspuns(conn, "Animale", "Elefant", 10);
                    insertRaspuns(conn, "Animale", "Emeu", 10);

                    // Nume
                    insertRaspuns(conn, "Nume", "Andrei", 10);
                    insertRaspuns(conn, "Nume", "Ana", 10);
                    insertRaspuns(conn, "Nume", "Bogdan", 10);
                    insertRaspuns(conn, "Nume", "Bianca", 10);
                    insertRaspuns(conn, "Nume", "Cristian", 10);
                    insertRaspuns(conn, "Nume", "Carmen", 10);
                    insertRaspuns(conn, "Nume", "Daniel", 10);
                    insertRaspuns(conn, "Nume", "Diana", 10);
                    insertRaspuns(conn, "Nume", "Elena", 10);
                    insertRaspuns(conn, "Nume", "Emil", 10);

                    // Masini
                    insertRaspuns(conn, "Masini", "Audi", 10);
                    insertRaspuns(conn, "Masini", "Alfa Romeo", 10);
                    insertRaspuns(conn, "Masini", "BMW", 10);
                    insertRaspuns(conn, "Masini", "Bentley", 10);
                    insertRaspuns(conn, "Masini", "Chevrolet", 10);
                    insertRaspuns(conn, "Masini", "Citroen", 10);
                    insertRaspuns(conn, "Masini", "Dacia", 10);
                    insertRaspuns(conn, "Masini", "Dodge", 10);
                    insertRaspuns(conn, "Masini", "Elva", 10);
                }
            }

            logger.info("Baza de date initializata cu succes.");
        } catch (Exception e) {
            logger.error("Eroare la initializarea bazei de date: " + e.getMessage());
        }
    }

    private void insertRaspuns(Connection conn, String cat, String rasp, int puncte) throws SQLException {
        String sql = "INSERT INTO raspunsuri (categorie, raspuns, puncte) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cat);
            stmt.setString(2, rasp);
            stmt.setInt(3, puncte);
            stmt.executeUpdate();
        }
    }
}
