package services;

import enums.ChangeEvent;
import exceptions.ServiceException;
import models.*;
import repo.*;
import utils.observer.Observable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class MainService extends Observable {
    private final LocationRepo locationRepo;
    private final HotelRepo hotelRepo;
    private final SpecialOfferRepo specialOffersRepo;
    private final ClientRepo clientRepo;
    private final ReservationRepo reservationRepo;
    private Client lastReservationClient;

    public MainService(LocationRepo locationRepo, HotelRepo hotelRepo, SpecialOfferRepo specialOffersRepo, ClientRepo clientRepo, ReservationRepo reservationRepo) {
        this.locationRepo = locationRepo;
        this.hotelRepo = hotelRepo;
        this.specialOffersRepo = specialOffersRepo;
        this.clientRepo = clientRepo;
        this.reservationRepo = reservationRepo;
    }

    public List<Location> findAllLocations() {
        return locationRepo.findAll();
    }

    public List<Hotel> findHotelsByLocation(String locationName){
        return hotelRepo.findByLocation(locationName);
    }

    public List<SpecialOffer> findSpecialOffers(Long hotelId, LocalDate date){
        return specialOffersRepo.findSpecialOffersInTimeline(hotelId, date);
    }

    public List<Client> findAllClients() {
        return clientRepo.findAll();
    }

    public Hotel findHotelById(Long hotelId) {
        Optional<Hotel> hotel = hotelRepo.findOne(hotelId);
        if(hotel.isEmpty())
            throw new ServiceException("Hotel with id " + hotelId + " not found");
        return hotel.get();
    }

    public Location findLocationById(Long locationId) {
        Optional<Location> location = locationRepo.findOne(locationId);
        if(location.isEmpty())
            throw new ServiceException("Location with id " + locationId + " not found");
        return location.get();
    }

    public List<SpecialOffer> findClientSpecialOffers(Client currentClient) {
        List<SpecialOffer> offers = specialOffersRepo.findAll();
        return offers.stream()
                .filter(offer->
                    !LocalDate.now().isBefore(offer.getStartDate()) && !LocalDate.now().isAfter(offer.getEndDate()) && currentClient.getFidelityGrade()>offer.getPercents()
                )
                .filter(offer-> !hotelRepo.isReserved(offer.getHotelId()))
                .toList();
    }

    public void saveReservation(Client currentClient, SpecialOffer selectedOffer,LocalDate date, int numberOfNights) {
        if(date.isBefore(LocalDate.now()))
            throw new ServiceException("Reservation date cannot be in the past!");
        if(date.isAfter(selectedOffer.getEndDate()) || date.isBefore(selectedOffer.getStartDate()))
            throw new ServiceException("Reservation date must be within the special offer period!");
        Reservation reservation = new Reservation(currentClient.getId(), selectedOffer.getHotelId(), date, numberOfNights);
        reservationRepo.save(reservation);
        lastReservationClient = currentClient;
        notifyObservers(ChangeEvent.RESERVATION_SAVED);
    }

    public Client getLastReservationClient() {
        return lastReservationClient;
    }
}
