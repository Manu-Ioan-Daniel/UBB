package network;

import java.io.Serializable;

public class Response implements Serializable {
    private final ResponseType type;
    private final Object data;
    private final String message;

    public Response(ResponseType type, Object data, String message) {
        this.type = type;
        this.data = data;
        this.message = message;
    }

    public static Response ok(Object data) {
        return new Response(ResponseType.OK, data, null);
    }

    public static Response error(String message) {
        return new Response(ResponseType.ERROR, null, message);
    }

    public ResponseType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
