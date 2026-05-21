package org.example.utils;

import org.example.dtos.RideRequest;
import org.example.dtos.RideResponse;
import org.example.models.Ride;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RideMapper {

    RideResponse toResponse(Ride ride);

    List<RideResponse> toResponseList(List<Ride> rides);

    Ride toEntity(RideRequest request);

}
