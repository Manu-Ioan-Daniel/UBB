package template.template.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import template.template.domain.Categorie;
import template.template.domain.Jucator;
import template.template.utils.Observer.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
@Setter
public class JocGameState extends Observable {
    private int m = 2; 
    private final List<Jucator> jucatoriActivi = new ArrayList<>();
    private final List<Categorie> categoriiSelectate = new ArrayList<>();
    private final ConcurrentHashMap<String, Integer> punctaje = new ConcurrentHashMap<>();
    private String statusMesaj = "Asteptam jucatori...";
    private boolean inceput = false;

    private int rundaCurenta = 1;
    private final List<Jucator> jucatoriSortati = new ArrayList<>();
    private String jucatorCareAlege = "";
    private String literaCurenta = "";

    private final ConcurrentHashMap<String, Map<String, String>> raspunsuriTrimise = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Integer> puncteRundaCurenta = new ConcurrentHashMap<>();

    private final List<Map<String, Integer>> istoricPuncteRunde = new ArrayList<>();
    private final List<Map<String, Map<String, String>>> istoricRaspunsuriRunde = new ArrayList<>();
    private final List<String> istoricLitereRunde = new ArrayList<>();

    private List<String> castigatoriJoc = new ArrayList<>();
    private int punctajCastigatori = 0;
    private final ConcurrentHashMap<String, List<Integer>> rundeCastigateJucatori = new ConcurrentHashMap<>();
    private Long idJocCurent = null;

    public synchronized void adaugaJucator(Jucator jucator) {
        if (jucatoriActivi.stream().anyMatch(j -> j.getPorecla().equals(jucator.getPorecla()))) {
            return;
        }
        
        if (jucatoriActivi.isEmpty()) {
            istoricPuncteRunde.clear();
            istoricRaspunsuriRunde.clear();
            istoricLitereRunde.clear();
            castigatoriJoc.clear();
            punctajCastigatori = 0;
            rundeCastigateJucatori.clear();
            idJocCurent = null;
        }

        jucatoriActivi.add(jucator);
        punctaje.put(jucator.getPorecla(), 0);
        puncteRundaCurenta.put(jucator.getPorecla(), 0);
        
        if (jucatoriActivi.size() >= m) {
            inceput = true;
            jucatoriSortati.clear();
            jucatoriSortati.addAll(jucatoriActivi);
            jucatoriSortati.sort((j1, j2) -> Integer.compare(j1.getVarsta(), j2.getVarsta()));
            rundaCurenta = 1;
            jucatorCareAlege = jucatoriSortati.get(0).getPorecla();
            literaCurenta = "";
            statusMesaj = "Asteptam ca " + jucatorCareAlege + " sa aleaga o litera";
        } else {
            inceput = false;
            statusMesaj = "Asteptam jucatori...";
        }
        notifyObservers();
    }

    public synchronized boolean esteJucatorInregistratInJoc(String porecla) {
        return jucatoriActivi.stream().anyMatch(j -> j.getPorecla().equals(porecla));
    }

    public synchronized void stergeJucator(String porecla) {
        jucatoriActivi.removeIf(j -> j.getPorecla().equals(porecla));
        punctaje.remove(porecla);
        puncteRundaCurenta.remove(porecla);
        raspunsuriTrimise.remove(porecla);
        if (jucatoriActivi.size() < m) {
            inceput = false;
            statusMesaj = "Asteptam jucatori...";
            categoriiSelectate.clear();
            jucatoriSortati.clear();
            jucatorCareAlege = "";
            literaCurenta = "";
            rundaCurenta = 1;
        }
        notifyObservers();
    }
    
    public synchronized void setCategoriiSelectate(List<Categorie> categorii) {
        this.categoriiSelectate.clear();
        this.categoriiSelectate.addAll(categorii);
        notifyObservers();
    }

    public synchronized void inregistreazaRaspunsuri(String porecla, Map<String, String> raspunsuri) {
        raspunsuriTrimise.put(porecla, raspunsuri);
    }
}
