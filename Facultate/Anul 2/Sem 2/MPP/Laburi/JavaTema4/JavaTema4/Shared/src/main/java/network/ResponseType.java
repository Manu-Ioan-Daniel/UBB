package network;

import java.io.Serializable;

public enum ResponseType implements Serializable {
    OK,
    ERROR,
    AUTH_SUCCES,
    AUTH_FAILED,
    RESERVATIONS_FOUND,
    RESERVATION_MADE,
    RESERVATION_CANCELED,
    RIDE_FOUND,
    RIDES_FOUND,
    UPDATE_NOTIFICATION
}
