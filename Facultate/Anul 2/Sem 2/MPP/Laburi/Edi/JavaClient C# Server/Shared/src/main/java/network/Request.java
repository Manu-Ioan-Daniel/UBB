package network;

import java.io.Serializable;

public class Request implements Serializable {
    private final RequestType type;
    private final Object data;

    public Request(RequestType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RequestType getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
