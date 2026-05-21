package org.example.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dtos.RideRequest;
import org.example.dtos.RideResponse;
import org.example.exceptions.ServiceException;
import org.example.models.Ride;
import org.example.repos.RidesRepo;
import org.example.utils.RideMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RideService {

    private final RidesRepo ridesRepo;
    private final RideMapper rideMapper;

    public Long createRide(RideRequest rideRequest) {

        Ride ride = rideMapper.toEntity(rideRequest);
        Ride saved = ridesRepo.save(ride);
        log.debug("Ride created with id={}", saved.getId());
        return saved.getId();

    }

    @Transactional
    public void modifyRide(Long id, RideRequest rideRequest) {

        Ride ride = ridesRepo.findById(id).orElseThrow(()->new ServiceException("Ride not found with id=" + id));
        ride.setDestination(rideRequest.getDestination());
        ride.setDepartureTime(rideRequest.getDepartureTime());
        ride.setRideDate(rideRequest.getRideDate());

        ridesRepo.save(ride);
        log.debug("Ride modified with id={}", ride.getId());

    }

    @Transactional
    public void deleteRide(Long rideId) {

        if(!ridesRepo.existsById(rideId)){
            throw new ServiceException("Ride not found with id=" + rideId);
        }

        ridesRepo.deleteById(rideId);

    }

    public RideResponse findRide(Long rideId) {

        return rideMapper.toResponse(ridesRepo.findById(rideId).orElse(null));

    }

    public List<RideResponse> findAllRides() {

        return rideMapper.toResponseList(ridesRepo.findAll());

    }

    public List<RideResponse> findAllRidesByDestination(String destination) {

        return rideMapper.toResponseList(ridesRepo.findAllByDestination(destination));

    }

}
