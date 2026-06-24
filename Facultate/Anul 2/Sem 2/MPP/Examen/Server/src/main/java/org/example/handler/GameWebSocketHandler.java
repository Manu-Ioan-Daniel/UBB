package org.example.handler;

import lombok.RequiredArgsConstructor;
import org.example.network.Message;
import org.example.service.PlayerService;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Handler principal WebSocket — gestionează toate conexiunile și mesajele.
 *
 * Spring apelează automat:
 *   afterConnectionEstablished() → când un browser se conectează
 *   handleTextMessage()          → când primim un mesaj de la browser
 *   afterConnectionClosed()      → când browserul se deconectează
 *
 * Sessions:
 *   Fiecare browser conectat are un WebSocketSession unic.
 *   Le stocăm în sessions (ConcurrentHashMap) indexate după porecla jucătorului.
 *   ConcurrentHashMap e thread-safe — Spring poate apela handler-ul din thread-uri diferite.
 *
 * ══════════════════════════════════════════════════════════════
 *  ADAPTEZI PER PROBLEMĂ:
 *  - handleTextMessage() → adaugi case-uri în switch pentru acțiunile din cerință
 *  - Adaugi câmpuri de stare (ex: configuratie, ronduriRamase, etc.)
 *  - Logica de business merge în metode private separate
 * ══════════════════════════════════════════════════════════════
 */
@Component
@RequiredArgsConstructor
public class GameWebSocketHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final PlayerService playerService;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final int n = 2;
    /**
     * Apelat când un browser deschide conexiunea WebSocket.
     * NU știm încă cine e — așteptăm mesajul HELLO cu porecla.
     */

    /**
     * Apelat când browserul se deconectează (tab închis, refresh, eroare rețea etc.)
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // Găsim și scoatem jucătorul din sessions după session id
        String porecla = getPoreclaBySession(session);
        if (porecla != null) {
            sessions.remove(porecla);
            System.out.println("[WS] Deconectat: " + porecla);

        }
    }

    /**
     * Apelat de Spring pentru fiecare mesaj text primit de la browser.
     *
     * ══════════════════════════════════════════
     *  MODIFICĂ ASTA PER PROBLEMĂ:
     *  Adaugi case-uri pentru fiecare acțiune
     * ══════════════════════════════════════════
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage){
        Message msg = Message.fromJson(textMessage.getPayload());
        System.out.println("[WS] Primit: " + msg);
        try {
            switch (msg.getType()) {

                case LOGIN -> handleLogin(session, msg);

                case DISCONNECT -> handleDisconnect(session, msg);

                default -> sendTo(session, Message.error("Tip mesaj necunoscut: " + msg.getType()));
            }
        }catch(Exception e) {
            System.out.println("[WS] Error: " + e.getMessage());
        }
    }

    /**
     * HELLO — jucătorul se prezintă cu porecla.
     * Verificăm că porecla nu e deja folosită și îl înregistrăm.
     */
    private void handleLogin(WebSocketSession session, Message msg){
        String porecla = msg.getSender();
        boolean what = playerService.playerExists(porecla);
        System.out.println("[WS] Player exists: " + what);
        if (porecla == null || porecla.isBlank()) {
            System.out.println("[LOGIN] porecla proasta");
            sendTo(session, Message.error("Porecla nu poate fi goală."));
            return;
        }

        if(!playerService.playerExists(porecla)){
            System.out.println("[LOGIN] nu exista in DB");
            sendTo(session,Message.error("Nu exista nici o porecla de genul prostutule"));
            return;
        }

        if (sessions.containsKey(porecla)) {
            System.out.println("[LOGIN] porecla luata deja");
            sendTo(session, Message.error("Porecla '" + porecla + "' e deja folosită."));
            return;
        }

        sessions.put(porecla, session);
        System.out.println("[WS] Jucător înregistrat: " + porecla + " (total: " + sessions.size() + ")");
        sendTo(session, Message.succes("Bun venit, " + porecla + "!"));
        if(sessions.size() == n){
            broadcast(Message.gameStatus("ready"));
            sendTo(sessions.values().toArray()[n%2], Message.gameConfig(playerService.getConfig));
        }
        else{
           sendTo(session, Message.gameStatus("not_ready"));
        }

    }

    /**
     * ACTION — jucătorul face ceva (alegere, input, decizie).
     *
     * ══════════════════════════════════════════
     *  ADAPTEZI ASTA PER PROBLEMĂ:
     *  payload conține datele acțiunii
     *  Ex: "3" pentru o alegere, "start" pentru o comandă, JSON pentru date complexe
     * ══════════════════════════════════════════
     */
    private void handleAction(WebSocketSession session, Message msg){
        String porecla = msg.getSender();

        if (!sessions.containsKey(porecla)) {
            sendTo(session, Message.error("Nu ești înregistrat. Trimite HELLO primul."));
            return;
        }

        String payload = msg.getPayload();
        System.out.println("[WS] Acțiune de la " + porecla + ": " + payload);

        // ── Logica ta de business merge aici ─────────────────────────────────────
        // Exemplu generic — înlocuiește cu logica problemei
        String rezultat = proceseazaActiune(porecla, payload);

        // Trimite rezultatul înapoi jucătorului
        sendTo(session, Message.response(rezultat));

        // Dacă acțiunea afectează toți jucătorii → broadcast
        // broadcast(Message.update("Ceva s-a schimbat: " + rezultat), null);
    }

    /**
     * DISCONNECT — jucătorul pleacă curat.
     */
    private void handleDisconnect(WebSocketSession session, Message msg) throws IOException {
        String porecla = msg.getSender();
        sessions.remove(porecla);
        sendTo(session, Message.disconnect("SERVER"));
        session.close();
        broadcast(Message.update(porecla + " s-a deconectat."));
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // UTILITAR — TRIMITERE MESAJE
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Trimite un mesaj unui singur jucător după porecla.
     */
    public void sendToPlayer(String porecla, Message msg) {
        WebSocketSession session = sessions.get(porecla);
        if (session != null && session.isOpen()) {
            sendTo(session, msg);
        }
    }

    /**
     * Trimite un mesaj tuturor jucătorilor conectați.
     *
     */
    public void broadcast(Message msg) {

        sessions.forEach((porecla, session) -> {
            if (session.isOpen()) {
                sendTo(session, msg);
            }
        });
    }

    /**
     * Trimitere efectivă pe sesiune — serializează Message la JSON și trimite.
     */
    private void sendTo(WebSocketSession session, Message msg) {
        try {
            session.sendMessage(new TextMessage(msg.toJson()));
        }catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // UTILITAR — QUERY SESSIONS
    // ══════════════════════════════════════════════════════════════════════════════

    /** Găsește porecla unui jucător după session id */
    private String getPoreclaBySession(WebSocketSession target) {
        return sessions.entrySet().stream()
            .filter(e -> e.getValue().getId().equals(target.getId()))
            .map(java.util.Map.Entry::getKey)
            .findFirst()
            .orElse(null);
    }

    /** Câți jucători sunt conectați acum */
    public int getJucatoriConectati() {
        return sessions.size();
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // LOGICA DE BUSINESS — ÎNLOCUIEȘTI ASTA
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Placeholder — înlocuiește cu logica reală din problemă.
     * Ex: verifici dacă acțiunea e validă, actualizezi starea jocului, etc.
     */
    private String proceseazaActiune(String porecla, String payload) {
        return "Acțiunea ta a fost primită: " + payload;
    }
}
