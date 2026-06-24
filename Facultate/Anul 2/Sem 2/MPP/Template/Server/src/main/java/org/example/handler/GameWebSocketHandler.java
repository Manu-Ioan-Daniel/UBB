package org.example.handler;

import org.example.network.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

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
public class GameWebSocketHandler extends TextWebSocketHandler {

    // ─── Stare globală ────────────────────────────────────────────────────────────

    // porecla → session  (știm cui să trimitem mesaje)
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    // ══════════════════════════════════════════════════════════════════════════════
    // LIFECYCLE
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Apelat când un browser deschide conexiunea WebSocket.
     * NU știm încă cine e — așteptăm mesajul HELLO cu porecla.
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("[WS] Conexiune nouă: " + session.getId());
        // Nu adăugăm în sessions încă — facem asta la HELLO când știm porecla
    }

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

            // ── Notifică restul jucătorilor ──────────────────────────────────────
            // Adaptează payload-ul după ce trebuie să vadă ceilalți
            broadcast(Message.update(porecla + " a plecat din joc."), null);
        }
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // PRIMIRE MESAJE
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * Apelat de Spring pentru fiecare mesaj text primit de la browser.
     *
     * ══════════════════════════════════════════
     *  MODIFICĂ ASTA PER PROBLEMĂ:
     *  Adaugi case-uri pentru fiecare acțiune
     * ══════════════════════════════════════════
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        Message msg = Message.fromJson(textMessage.getPayload());
        System.out.println("[WS] Primit: " + msg);

        switch (msg.getType()) {

            case HELLO -> handleHello(session, msg);

            case ACTION -> handleAction(session, msg);

            case DISCONNECT -> handleDisconnect(session, msg);

            default -> sendTo(session, Message.error("Tip mesaj necunoscut: " + msg.getType()));
        }
    }

    // ══════════════════════════════════════════════════════════════════════════════
    // HANDLERE PER TIP MESAJ
    // ══════════════════════════════════════════════════════════════════════════════

    /**
     * HELLO — jucătorul se prezintă cu porecla.
     * Verificăm că porecla nu e deja folosită și îl înregistrăm.
     */
    private void handleHello(WebSocketSession session, Message msg) throws IOException {
        String porecla = msg.getSender();

        if (porecla == null || porecla.isBlank()) {
            sendTo(session, Message.error("Porecla nu poate fi goală."));
            return;
        }

        if (sessions.containsKey(porecla)) {
            sendTo(session, Message.error("Porecla '" + porecla + "' e deja folosită."));
            return;
        }

        // Înregistrăm jucătorul
        sessions.put(porecla, session);
        System.out.println("[WS] Jucător înregistrat: " + porecla + " (total: " + sessions.size() + ")");

        // Răspuns direct jucătorului nou
        sendTo(session, Message.response("Bun venit, " + porecla + "!"));

        // Anunță pe toți ceilalți
        broadcast(Message.update(porecla + " a intrat în joc."), porecla);
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
    private void handleAction(WebSocketSession session, Message msg) throws IOException {
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
        broadcast(Message.update(porecla + " s-a deconectat."), null);
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
            try {
                sendTo(session, msg);
            } catch (IOException e) {
                System.err.println("[WS] Eroare trimitere la " + porecla + ": " + e.getMessage());
            }
        }
    }

    /**
     * Trimite un mesaj tuturor jucătorilor conectați.
     *
     * @param excludePorecla dacă nu e null, sărim peste acel jucător
     *                       (util când vrei să trimiți tuturor ALTORA decât cel care a acționat)
     */
    public void broadcast(Message msg, String excludePorecla) {
        sessions.forEach((porecla, session) -> {
            if (porecla.equals(excludePorecla)) return; // sărим peste jucătorul exclus

            if (session.isOpen()) {
                try {
                    sendTo(session, msg);
                } catch (IOException e) {
                    System.err.println("[WS] Eroare broadcast la " + porecla + ": " + e.getMessage());
                }
            }
        });
    }

    /**
     * Trimitere efectivă pe sesiune — serializează Message la JSON și trimite.
     */
    private void sendTo(WebSocketSession session, Message msg) throws IOException {
        session.sendMessage(new TextMessage(msg.toJson()));
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
