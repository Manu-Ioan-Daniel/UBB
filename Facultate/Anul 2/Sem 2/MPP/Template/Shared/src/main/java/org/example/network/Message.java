package org.example.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DTO schimbat între browser și server ca JSON peste WebSocket.
 *
 * Fiecare mesaj are:
 *   - type    → ce fel de eveniment e (HELLO, ACTION, UPDATE etc.)
 *   - sender  → cine a trimis (porecla jucătorului sau "SERVER")
 *   - payload → datele efective ca String (poate fi JSON nested, număr, text liber)
 *
 * ══════════════════════════════════════════
 *  ADAPTEZI PER PROBLEMĂ:
 *  - Adaugi valori în Type pentru fiecare acțiune din cerință
 *  - payload îl parsezi în handler după nevoie (ex: Integer.parseInt, new ObjectMapper().readTree)
 * ══════════════════════════════════════════
 */
@JsonIgnoreProperties(ignoreUnknown = true) // câmpuri necunoscute → ignorate, fără excepție
public class Message {

    // ─── Tipuri ───────────────────────────────────────────────────────────────────
    // Adaugă/modifică după cerință
    public enum Type {
        // Browser → Server
        HELLO,       // primul mesaj — clientul se prezintă cu porecla
        ACTION,      // acțiunea unui jucător (alegere, decizie, input)

        // Server → Browser (broadcast sau direct)
        UPDATE,      // stare nouă trimisă tuturor (ex: toți văd același lucru)
        RESPONSE,    // răspuns direct unui singur jucător
        ERROR,       // eroare de logică sau validare

        // Ambele sensuri
        DISCONNECT   // deconectare curată
    }

    // ─── Câmpuri ──────────────────────────────────────────────────────────────────
    private Type   type;
    private String sender;   // porecla jucătorului sau "SERVER"
    private String payload;  // date specifice — adaptează per problemă

    // ─── Constructori ─────────────────────────────────────────────────────────────

    public Message() {} // necesar pentru Jackson

    public Message(Type type, String sender, String payload) {
        this.type    = type;
        this.sender  = sender;
        this.payload = payload;
    }

    // ─── Factory methods ──────────────────────────────────────────────────────────

    public static Message update(String payload)           { return new Message(Type.UPDATE,    "SERVER", payload); }
    public static Message response(String payload)         { return new Message(Type.RESPONSE,  "SERVER", payload); }
    public static Message error(String motiv)              { return new Message(Type.ERROR,      "SERVER", motiv);  }
    public static Message disconnect(String sender)        { return new Message(Type.DISCONNECT, sender,   "bye");  }

    // ─── Helper — serializare rapidă ──────────────────────────────────────────────

    private static final ObjectMapper MAPPER = new ObjectMapper();

    /** Convertește mesajul la JSON string — folosit în handler înainte de sendMessage() */
    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Eroare serializare Message", e);
        }
    }

    /** Parsează un JSON string într-un Message — folosit în handler la primire */
    public static Message fromJson(String json) {
        try {
            return MAPPER.readValue(json, Message.class);
        } catch (Exception e) {
            throw new RuntimeException("Eroare deserializare Message: " + json, e);
        }
    }

    // ─── Getteri / Setteri ────────────────────────────────────────────────────────

    public Type   getType()    { return type;    }
    public String getSender()  { return sender;  }
    public String getPayload() { return payload; }

    public void setType   (Type   type)    { this.type    = type;    }
    public void setSender (String sender)  { this.sender  = sender;  }
    public void setPayload(String payload) { this.payload = payload; }

    @Override
    public String toString() {
        return "[" + type + "][" + sender + "] " + payload;
    }
}
