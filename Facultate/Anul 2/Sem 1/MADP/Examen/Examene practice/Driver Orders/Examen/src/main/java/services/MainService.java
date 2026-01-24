package services;

import enums.ChangeEvent;
import enums.OrderStatus;
import models.Driver;
import models.Order;
import repo.DriverDbRepository;
import repo.OrderDbRepository;
import utils.observer.Observable;
import validator.Validator;
import java.time.LocalDateTime;
import java.util.List;

public class MainService extends Observable {

    private final OrderDbRepository orderRepository;
    private final DriverDbRepository driverRepository;
    private final Validator<Order> orderValidator;
    private Long driverToNotifyId;
    private int  driverToNotifyIndex = 0;


    public MainService(OrderDbRepository orderRepository, DriverDbRepository driverRepository, Validator<Order> orderValidator) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.orderValidator = orderValidator;
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
        assignOrder();
    }

    public void assignOrder() {
        List<Driver> qualifiedDrivers = findQualifiedDrivers();
        if(driverToNotifyIndex>=qualifiedDrivers.size()){
            driverToNotifyIndex = 0;
        }
        driverToNotifyId = qualifiedDrivers.get(driverToNotifyIndex).getId();
        notifyObservers(ChangeEvent.ORDER_ASSIGNED);
    }

    public List<Driver> findQualifiedDrivers() {
        return driverRepository.findAll().stream()

                .filter(driver ->
                        orderRepository.findDriverOrders(driver.getId()).stream()
                                .noneMatch(order -> order.getStatus() == OrderStatus.IN_PROGRESS)
                )

                .sorted((d1, d2) -> {
                    LocalDateTime last1 = getLastFinishedOrderEndDate(d1);
                    LocalDateTime last2 = getLastFinishedOrderEndDate(d2);

                    return last1.compareTo(last2);
                })
                .toList();
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
        o.setStartDate(LocalDateTime.now());
        o.setDriverId(id);
        orderRepository.update(o);
        driverToNotifyIndex= 0;
        driverToNotifyId = null;
        notifyObservers(ChangeEvent.ORDER_UPDATED);
    }

    public String getOrderString() {
        Order o = getLastOrder();
        return "New Order " + o.getPickupAdress() + "->" + o.getDestinationAdress();
    }

    public Long getDriverToNotifyId() {
        return driverToNotifyId;
    }

    public void incrementDriverToNotifyIndex() {
        driverToNotifyIndex++;
    }
}
