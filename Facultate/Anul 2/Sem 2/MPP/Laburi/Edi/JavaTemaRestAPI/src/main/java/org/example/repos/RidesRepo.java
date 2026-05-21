package org.example.repos;

import org.example.models.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RidesRepo extends JpaRepository<Ride, Long> {

   List<Ride> findAllByDestination(String destination);

}
