package org.example.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class RideRequest {

    @NotBlank
    private String destination;

    @NotNull
    private LocalDate rideDate;

    @NotNull
    private LocalTime departureTime;

    @NotNull
    private Integer availableSeats;
}
