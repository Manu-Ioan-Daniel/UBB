package main;

import models.Employee;
import models.Reservation;
import models.Ride;
import repos.EmployeeDBRepo;
import repos.ReservationsDBRepo;
import repos.RidesDBRepo;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {

        Properties props = loadProperties();
        EmployeeDBRepo employeeRepo = new EmployeeDBRepo(props);
        RidesDBRepo ridesRepo = new RidesDBRepo(props);
        ReservationsDBRepo reservationsRepo = new ReservationsDBRepo(props);

        runEmployee(employeeRepo);
        runRideAndReservationOps(ridesRepo, reservationsRepo);

    }

    private static Properties loadProperties() {

        Properties props = new Properties();
        try{
            props.load(new FileReader("src/main/resources/bd.config"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return props;
    }

    private static void runEmployee(EmployeeDBRepo employeeRepo) {

        Employee employee1 = new Employee("emp1", "pass1", "Demo Employee 1", "Office 1");
        Employee employee2 = new Employee("emp2", "pass2", "Demo Employee 2", "Office 2");

        employeeRepo.save(employee1);
        employeeRepo.save(employee2);

        Optional<Employee> savedEmployee1 = employeeRepo.findByUsername("emp1");
        Optional<Employee> savedEmployee2 = employeeRepo.findByUsername("emp2");

        if(savedEmployee1.isEmpty() || savedEmployee2.isEmpty()) {
            throw new RuntimeException("Could not find inserted employees for demo operations");
        }

        Employee employeeToUpdate = new Employee("emp1", "pass1", "Demo Employee 1 Updated", "Office 1 Updated");
        employeeToUpdate.setId(savedEmployee1.get().getId());
        employeeRepo.update(employeeToUpdate);

        employeeRepo.delete(savedEmployee1.get().getId());
        employeeRepo.delete(savedEmployee2.get().getId());
    }

    private static void runRideAndReservationOps(RidesDBRepo ridesRepo, ReservationsDBRepo reservationsRepo) {

        Ride ride1 = new Ride("dest1", LocalDate.of(2026, 3, 20), LocalTime.of(10, 0));
        Ride ride2 = new Ride("dest2", LocalDate.of(2026, 3, 21), LocalTime.of(12, 30));

        ridesRepo.save(ride1);
        ridesRepo.save(ride2);

        List<Ride> allRides = ridesRepo.findAll();
        Optional<Ride> savedRide1 = Optional.ofNullable(allRides.get(0));
        Optional<Ride> savedRide2 = Optional.ofNullable(allRides.get(1));


        if (savedRide1.isEmpty() || savedRide2.isEmpty()) {
            throw new RuntimeException("Could not find inserted rides for demo operations");
        }

        Ride rideToUpdate = new Ride(savedRide1.get().getDestination(), savedRide1.get().getDate(), savedRide1.get().getDepartureTime());
        rideToUpdate.setId(savedRide1.get().getId());
        rideToUpdate.setAvailableSeats(10);
        ridesRepo.update(rideToUpdate);

        Reservation reservation1 = new Reservation("client1", 2, savedRide1.get().getId());
        Reservation reservation2 = new Reservation("client2", 3, savedRide2.get().getId());

        reservationsRepo.save(reservation1);
        reservationsRepo.save(reservation2);

        List<Reservation> allReservations = reservationsRepo.findAll();
        Optional<Reservation> savedReservation1 = Optional.ofNullable(allReservations.get(0));
        Optional<Reservation> savedReservation2 = Optional.ofNullable(allReservations.get(1));

        if (savedReservation1.isPresent()) {
            Reservation reservationToUpdate = new Reservation(savedReservation1.get().getClientName(), 1, savedReservation1.get().getRideId());
            reservationToUpdate.setId(savedReservation1.get().getId());
            reservationsRepo.update(reservationToUpdate);
        }

        reservationsRepo.delete(savedReservation1.get().getId());
        reservationsRepo.delete(savedReservation2.get().getId());

        ridesRepo.delete(savedRide1.get().getId());
        ridesRepo.delete(savedRide2.get().getId());
    }




}
