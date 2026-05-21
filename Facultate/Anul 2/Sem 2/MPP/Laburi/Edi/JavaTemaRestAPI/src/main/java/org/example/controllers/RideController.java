package org.example.controllers;


import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.example.dtos.RideRequest;
import org.example.dtos.RideResponse;
import org.example.services.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rides")
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping
    public ResponseEntity<@NonNull List<RideResponse>> findAllRides(@RequestParam(required = false) String destination){
        if (destination != null && !destination.isBlank()) {
            return ResponseEntity.ok(rideService.findAllRidesByDestination(destination));
        }
        return ResponseEntity.ok(rideService.findAllRides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<@NonNull RideResponse> findRide(@PathVariable Long id){
        return ResponseEntity.ok(rideService.findRide(id));
    }

    @PostMapping
    public ResponseEntity<?> createRide(@RequestBody RideRequest request){
        Long rideId = rideService.createRide(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("rideId", rideId));}

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRide(@PathVariable Long id, @RequestBody RideRequest request){
        rideService.modifyRide(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRide(@PathVariable Long id){
        rideService.deleteRide(id);
        return ResponseEntity.noContent().build();
    }

}
