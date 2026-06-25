package org.example.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.example.enums.Type;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message {

    private Type type;
    private String sender;
    private String payload;

    public static Message response(String payload){
        return new Message(Type.RESPONSE,  "SERVER", payload);
    }
//    public static Message error(String motiv)              { return new Message(Type.ERROR,      "SERVER", motiv);  }

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String toJson() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (Exception e) {
            throw new RuntimeException("Eroare serializare Message", e);
        }
    }

    @Override
    public String toString() {
        return "[" + type + "][" + sender + "] " + payload;
    }
}
