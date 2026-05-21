package org.example.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "rides")
public class Ride extends BaseEntity {

    @Column(nullable = false)
    private String destination;

    @Column(name = "date", nullable = false)
    private LocalDate rideDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "available_seats", nullable = false)
    @Setter
    private Integer availableSeats = 18;

    public Ride(String destination, LocalDate rideDate, LocalTime departureTime) {
        this.destination = destination;
        this.rideDate = rideDate;
        this.departureTime = departureTime;
    }

}
