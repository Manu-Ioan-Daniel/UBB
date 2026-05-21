package org.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class RideResponse {

    private Long id;
    private LocalDate rideDate;
    private LocalTime departureTime;
    private String destination;
    private Integer availableSeats;

}
