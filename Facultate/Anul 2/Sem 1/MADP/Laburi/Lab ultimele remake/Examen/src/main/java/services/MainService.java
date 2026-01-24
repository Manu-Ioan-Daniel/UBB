package services;

import enums.ChangeEvent;
import enums.OrderStatus;
import exceptions.ServiceException;
import models.Driver;
import models.Notification;
import models.Order;
import repo.DriverDbRepository;
import repo.NotificationRepo;
import repo.OrderDbRepository;
import utils.observer.Observable;
import validator.Validator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MainService extends Observable {

    private final NotificationRepo notificationRepo;
    private final OrderDbRepository orderRepository;
    private final DriverDbRepository driverRepository;
    private final Validator<Notification> notificationValidator;
    private final Validator<Order> orderValidator;



    public MainService(NotificationRepo repo, OrderDbRepository orderRepository, DriverDbRepository driverRepository, Validator<Notification> validator, Validator<Order> orderValidator) {
        this.notificationRepo = repo;
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.notificationValidator = validator;
        this.orderValidator = orderValidator;

    }

    public void save(Notification notification) {
        if(notificationRepo.findOne(notification.getId()).isPresent()) {
            throw new ServiceException("Notification already exists");
        }
        notificationRepo.save(notification);
        notifyObservers(ChangeEvent.NOTIFICATION);
    }

    public void delete(Long id) {
        notificationRepo.delete(id);
        notifyObservers(ChangeEvent.NOTIFICATION);
    }

    public List<Notification> findNotifications(Long toId) {
        return notificationRepo.findNotifications(toId);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    public List<Order> findActiveOrdersByDriverId(Long driverId){
        return orderRepository.findActiveOrdersByDriverId(driverId);
    }

    public List<Driver> getAllDrivers(){
        return driverRepository.findAll();
    }

    public void finishOrder(Order order) {
        order.setStatus(OrderStatus.FINISHED);
        order.setEndDate(LocalDateTime.now());
        orderRepository.update(order);
        notifyObservers(ChangeEvent.ORDER_FINISHED);
    }

    public void addOrder(String clientName, String pickupAdress, String destinationAdress) {
        Order order = new Order(null, OrderStatus.PENDING, LocalDateTime.now(), null, pickupAdress, destinationAdress, clientName);
        orderValidator.validate(order);
        orderRepository.save(order);
        notifyObservers(ChangeEvent.ORDER_ADDED);
    }

    public Driver findQualifiedDriver() {
        return driverRepository.findAll().stream()

                .filter(driver ->
                        orderRepository.findDriverOrders(driver.getId()).stream()
                                .noneMatch(order -> order.getStatus() == OrderStatus.IN_PROGRESS)
                )

                .max((d1, d2) -> {
                    LocalDateTime last1 = getLastFinishedOrderEndDate(d1);
                    LocalDateTime last2 = getLastFinishedOrderEndDate(d2);

                    return last2.compareTo(last1);
                })
                .orElseThrow(() -> new ServiceException("No available drivers"));
    }

    private LocalDateTime getLastFinishedOrderEndDate(Driver driver) {
        return orderRepository.findDriverOrders(driver.getId()).stream()
                .filter(order -> order.getStatus() == OrderStatus.FINISHED)
                .map(Order::getEndDate)
                .max(LocalDateTime::compareTo)
                .orElse(LocalDateTime.MIN);
    }


    public Order getLastOrder() {
        return orderRepository.getLastOrder();
    }

    public void updateOrder(Long id) {
        Order o = getLastOrder();
        o.setStatus(OrderStatus.IN_PROGRESS);
        o.setEndDate(LocalDateTime.now());
        o.setDriverId(id);
        orderRepository.update(o);
        notifyObservers(ChangeEvent.ORDER_UPDATED);
    }

    public String getOrderString() {
        Order o = getLastOrder();
        return "New Order " + o.getPickupAdress() + "->" + o.getDestinationAdress();
    }
}
