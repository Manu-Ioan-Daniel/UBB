package models;

import enums.OrderStatus;

import java.time.LocalDateTime;

public class Order extends Entity<Long> {
    private Long driverId;
    private OrderStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final String pickupAdress;
    private final String destinationAdress;
    private final String clientName;

    public Order(Long driverId, OrderStatus status, LocalDateTime startDate, LocalDateTime endDate,
                 String pickupAdress, String destinationAdress, String clientName) {
        this.driverId = driverId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.pickupAdress = pickupAdress;
        this.destinationAdress = destinationAdress;
        this.clientName = clientName;
    }

    public Long getDriverId() {
        return driverId;
    }
    public void setDriverId(Long id) {
        this.driverId = id;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getPickupAdress() {
        return pickupAdress;
    }

    public String getDestinationAdress() {
        return destinationAdress;
    }

    public String getClientName() {
        return clientName;
    }


    public void setStartDate(LocalDateTime now) {
        this.startDate = now;
    }


    public void setEndDate(LocalDateTime now) {
        this.endDate = now;
    }
}
