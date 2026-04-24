package dtos;

import java.io.Serializable;

public class GetRideDTO implements Serializable {

    private final Long rideId;

    public GetRideDTO(Long rideId) {
        this.rideId = rideId;
    }

    public Long getRideId() {
        return rideId;
    }
}
