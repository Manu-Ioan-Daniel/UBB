package template.template.service;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import template.template.domain.Categorie;
import template.template.domain.Jucator;
import template.template.repository.CategorieRepository;
import template.template.repository.JucatorRepository;
import template.template.repository.RaspunsRepository;

import jakarta.annotation.PostConstruct;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JocService {

    private static final Logger logger = LogManager.getLogger(JocService.class);

    private final JucatorRepository jucatorRepository;
    private final CategorieRepository categorieRepository;
    private final RaspunsRepository raspunsRepository;
    private final JocGameState jocGameState;

    private final List<String> literePermise = new ArrayList<>(List.of("A", "B", "C", "D", "E"));

    @Value("${game.m:2}")
    private int m;

    @PostConstruct
    public void configureGame() {
        jocGameState.setM(m);
        logger.info("JocService configurat cu M = " + m);
    }

    public Optional<Jucator> gasesteJucator(String porecla) {
        logger.info("Cautare jucator in DB: porecla=" + porecla);
        return jucatorRepository.findByPorecla(porecla);
    }

    public synchronized void intrareJoc(Jucator jucator, int varsta) {
        logger.info("Intrare in joc pentru: " + jucator.getPorecla() + ", actualizare varsta=" + varsta);
        jucator.setVarsta(varsta);
        jucatorRepository.update(jucator);
        
        jocGameState.adaugaJucator(jucator);

        if (jocGameState.isInceput() && jocGameState.getCategoriiSelectate().isEmpty()) {
            logger.info("M jucatori conectati. Se aleg M+1 categorii.");
            List<Categorie> toateCategoriile = categorieRepository.findAll();
            Collections.shuffle(toateCategoriile);
            int nrCategoriiDeAles = m + 1;
            List<Categorie> categoriiAlese = toateCategoriile.subList(0, Math.min(nrCategoriiDeAles, toateCategoriile.size()));
            jocGameState.setCategoriiSelectate(categoriiAlese);
        }
    }

    public synchronized void iesireJoc(String porecla) {
        logger.info("Iesire din joc: " + porecla);
        jocGameState.stergeJucator(porecla);
    }

    public synchronized void alegeLitera(String porecla) {
        logger.info("Incercare alegere litera de catre: " + porecla);
        if (!jocGameState.isInceput()) {
            logger.warn("Jocul nu a inceput inca.");
            throw new IllegalStateException("Jocul nu a inceput");
        }
        if (!jocGameState.getJucatorCareAlege().equals(porecla)) {
            logger.warn("Nu este randul lui " + porecla + " sa aleaga.");
            throw new IllegalArgumentException("Nu este randul tau sa alegi litera");
        }
        if (!jocGameState.getLiteraCurenta().isEmpty()) {
            logger.warn("Litera a fost deja aleasa in runda curenta.");
            throw new IllegalStateException("Litera a fost deja aleasa");
        }

        Collections.shuffle(literePermise);
        String literaAleasa = literePermise.get(0);
        jocGameState.setLiteraCurenta(literaAleasa);
        jocGameState.setStatusMesaj("Litera generata este: " + literaAleasa);
        jocGameState.notifyObservers();
        logger.info("Litera aleasa cu succes: " + literaAleasa);
    }

    public synchronized void trimiteRaspunsuri(String porecla, Map<String, String> raspunsuri) {
        logger.info("Submisie raspunsuri de la: " + porecla);
        if (!jocGameState.isInceput()) {
            throw new IllegalStateException("Jocul nu a inceput");
        }
        if (jocGameState.getLiteraCurenta().isEmpty()) {
            throw new IllegalStateException("Litera nu a fost aleasa");
        }
        if (jocGameState.getRaspunsuriTrimise().containsKey(porecla)) {
            throw new IllegalStateException("Ai trimis deja raspunsurile in aceasta runda");
        }

        jocGameState.inregistreazaRaspunsuri(porecla, raspunsuri);

        if (jocGameState.getRaspunsuriTrimise().size() == jocGameState.getJucatoriActivi().size()) {
            evaluareRunda();
        } else {
            jocGameState.notifyObservers();
        }
    }

    private void evaluareRunda() {
        logger.info("Incepe evaluare runda: " + jocGameState.getRundaCurenta());
        String litera = jocGameState.getLiteraCurenta().toLowerCase();

        Map<String, Map<String, String>> raspunsuriCopiaza = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> entry : jocGameState.getRaspunsuriTrimise().entrySet()) {
            raspunsuriCopiaza.put(entry.getKey(), new HashMap<>(entry.getValue()));
        }
        jocGameState.getIstoricRaspunsuriRunde().add(raspunsuriCopiaza);
        jocGameState.getIstoricLitereRunde().add(jocGameState.getLiteraCurenta());

        for (Jucator jucator : jocGameState.getJucatoriActivi()) {
            String porecla = jucator.getPorecla();
            Map<String, String> raspunsuriJucator = jocGameState.getRaspunsuriTrimise().get(porecla);
            int puncteRunda = 0;

            for (Categorie cat : jocGameState.getCategoriiSelectate()) {
                String raspuns = raspunsuriJucator.getOrDefault(cat.getNume(), "").trim();

                if (raspuns.isEmpty() || !raspuns.toLowerCase().startsWith(litera)) {
                    continue;
                }

                Optional<Integer> puncteOpt = raspunsRepository.gasestePuncte(cat.getNume(), raspuns);
                if (puncteOpt.isPresent()) {
                    puncteRunda += puncteOpt.get();
                }
            }

            jocGameState.getPunteRundaCurenta().put(porecla, puncteRunda);
            int totalNou = jocGameState.getPunctaje().getOrDefault(porecla, 0) + puncteRunda;
            jocGameState.getPunctaje().put(porecla, totalNou);
        }

        jocGameState.getIstoricPuncteRunde().add(new HashMap<>(jocGameState.getPunteRundaCurenta()));

        int runda = jocGameState.getRundaCurenta();
        if (runda < m) {
            jocGameState.setRundaCurenta(runda + 1);
            jocGameState.setJucatorCareAlege(jocGameState.getJucatoriSortati().get(runda).getPorecla());
            jocGameState.setLiteraCurenta("");
            jocGameState.getRaspunsuriTrimise().clear();
            jocGameState.setStatusMesaj("Runda anterioara s-a terminat. Asteptam ca " + jocGameState.getJucatorCareAlege() + " sa aleaga litera.");
        } else {
            for (Jucator jucator : jocGameState.getJucatoriActivi()) {
                jocGameState.getRundeCastigateJucatori().put(jucator.getPorecla(), new ArrayList<>());
            }
            for (int r = 0; r < jocGameState.getIstoricPuncteRunde().size(); r++) {
                Map<String, Integer> puncteRundaMap = jocGameState.getIstoricPuncteRunde().get(r);
                int maxPuncte = -1;
                for (int pct : puncteRundaMap.values()) {
                    if (pct > maxPuncte) {
                        maxPuncte = pct;
                    }
                }
                if (maxPuncte >= 0) {
                    for (Map.Entry<String, Integer> entry : puncteRundaMap.entrySet()) {
                        if (entry.getValue() == maxPuncte) {
                            jocGameState.getRundeCastigateJucatori().get(entry.getKey()).add(r + 1);
                        }
                    }
                }
            }

            int maxTotal = -1;
            List<String> castigatori = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : jocGameState.getPunctaje().entrySet()) {
                if (entry.getValue() > maxTotal) {
                    maxTotal = entry.getValue();
                    castigatori.clear();
                    castigatori.add(entry.getKey());
                } else if (entry.getValue() == maxTotal) {
                    castigatori.add(entry.getKey());
                }
            }
            jocGameState.setCastigatoriJoc(castigatori);
            jocGameState.setPunctajCastigatori(maxTotal);
            jocGameState.setStatusMesaj("Jocul s-a terminat");

            salveazaIstoricJoc();
        }

        jocGameState.notifyObservers();
    }

    private void salveazaIstoricJoc() {
        logger.info("Salvare istoric joc in DB...");
        String insertJocSql = "INSERT INTO jocuri (m) VALUES (?)";
        String insertJucatorSql = "INSERT INTO joc_jucatori (id_joc, porecla, total_puncte, runde_castigate) VALUES (?, ?, ?, ?)";
        String insertRaspunsSql = "INSERT INTO istoric_raspunsuri (id_joc, runda, porecla, categorie, raspuns, puncte) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/TariOraseMPP", "root", "")) {
            conn.setAutoCommit(false);

            long idJoc;
            try (PreparedStatement stmt = conn.prepareStatement(insertJocSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, m);
                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        idJoc = rs.getLong(1);
                    } else {
                        throw new SQLException("Eroare la generare ID.");
                    }
                }
            }

            jocGameState.setIdJocCurent(idJoc);

            for (Jucator jucator : jocGameState.getJucatoriActivi()) {
                String porecla = jucator.getPorecla();
                int totalPuncte = jocGameState.getPunctaje().getOrDefault(porecla, 0);
                int rundeCastigate = jocGameState.getRundeCastigateJucatori().getOrDefault(porecla, new ArrayList<>()).size();

                try (PreparedStatement stmt = conn.prepareStatement(insertJucatorSql)) {
                    stmt.setLong(1, idJoc);
                    stmt.setString(2, porecla);
                    stmt.setInt(3, totalPuncte);
                    stmt.setInt(4, rundeCastigate);
                    stmt.executeUpdate();
                }
            }

            for (int r = 0; r < jocGameState.getIstoricRaspunsuriRunde().size(); r++) {
                int nrRunda = r + 1;
                String literaRunda = jocGameState.getIstoricLitereRunde().get(r).toLowerCase();
                Map<String, Map<String, String>> raspunsuriRundaMap = jocGameState.getIstoricRaspunsuriRunde().get(r);

                for (Map.Entry<String, Map<String, String>> entryJucator : raspunsuriRundaMap.entrySet()) {
                    String porecla = entryJucator.getKey();
                    Map<String, String> raspunsuriJucator = entryJucator.getValue();

                    for (Map.Entry<String, String> entryResp : raspunsuriJucator.entrySet()) {
                        String categorie = entryResp.getKey();
                        String raspuns = entryResp.getValue().trim();
                        int puncte = 0;
                        if (!raspuns.isEmpty() && raspuns.toLowerCase().startsWith(literaRunda)) {
                            Optional<Integer> puncteOpt = raspunsRepository.gasestePuncte(categorie, raspuns);
                            if (puncteOpt.isPresent()) {
                                puncte = puncteOpt.get();
                            }
                        }

                        try (PreparedStatement stmt = conn.prepareStatement(insertRaspunsSql)) {
                            stmt.setLong(1, idJoc);
                            stmt.setInt(2, nrRunda);
                            stmt.setString(3, porecla);
                            stmt.setString(4, categorie);
                            stmt.setString(5, raspuns);
                            stmt.setInt(6, puncte);
                            stmt.executeUpdate();
                        }
                    }
                }
            }
            conn.commit();
            logger.info("Salvare istoric reusita. ID joc: " + idJoc);
        } catch (SQLException e) {
            logger.error("Eroare la salvare istoric: " + e.getMessage());
        }
    }

    public List<Map<String, Object>> getGameWinnersData(long idJoc) throws Exception {
        int gameM = raspunsRepository.getMForGame(idJoc);
        if (gameM == 0) {
            throw new IllegalArgumentException("Jocul nu exista");
        }

        int minRunde = gameM / 2;
        List<String> castigatori = raspunsRepository.getWinnersForGame(idJoc, minRunde);
        List<Map<String, Object>> list = new ArrayList<>();

        for (String porecla : castigatori) {
            Map<String, Object> data = new HashMap<>();
            data.put("porecla", porecla);
            data.put("puncteRunde", raspunsRepository.getPuncteRundeForPlayer(idJoc, porecla));
            data.put("raspunsuri", raspunsRepository.getRaspunsuriForPlayer(idJoc, porecla));
            list.add(data);
        }
        return list;
    }
}
