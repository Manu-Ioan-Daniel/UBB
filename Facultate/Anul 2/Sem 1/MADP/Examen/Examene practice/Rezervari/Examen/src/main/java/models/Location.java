package models;

public class Location extends Entity<Long>{
    private final String locationName;

    public Location(String locationName) {
        this.locationName = locationName;
    }
    public String getLocationName() {
        return locationName;
    }
}

