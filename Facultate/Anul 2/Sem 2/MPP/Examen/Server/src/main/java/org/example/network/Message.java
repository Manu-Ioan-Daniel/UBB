package org.example.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.example.enums.Type;

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
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    private Type type;
    private String sender;
    private String payload;



    public static Message update(String payload)           { return new Message(Type.UPDATE,    "SERVER", payload); }
    public static Message response(String payload)         { return new Message(Type.RESPONSE,  "SERVER", payload); }
    public static Message error(String motiv)              { return new Message(Type.ERROR,      "SERVER", motiv);  }
    public static Message disconnect(String sender)        { return new Message(Type.DISCONNECT, sender,   "bye");  }
    public static Message succes(String payload)           {return new Message(Type.SUCCES, "SERVER", payload);}
    public static Message gameStatus(String payload)       {return new Message(Type.STATUS, "SERVER", payload);}
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

    @Override
    public String toString() {
        return "[" + type + "][" + sender + "] " + payload;
    }
}
