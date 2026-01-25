package models;

import enums.HotelType;

public class Hotel extends Entity<Long> {
    private final Long locationId;
    private final String hotelName;
    private final int noRooms;
    private final double pricePerNight;
    private final HotelType type;

    public Hotel(Long locationId, String hotelName, int noRooms, double pricePerNight, HotelType type) {
        this.locationId = locationId;
        this.hotelName = hotelName;
        this.noRooms = noRooms;
        this.pricePerNight = pricePerNight;
        this.type = type;
    }

    public Long getLocationId() {
        return locationId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public int getNoRooms() {
        return noRooms;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public HotelType getType() {
        return type;
    }

}
