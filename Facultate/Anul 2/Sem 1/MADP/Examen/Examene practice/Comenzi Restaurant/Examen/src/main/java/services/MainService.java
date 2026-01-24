package services;
import enums.ChangeEvent;
import enums.OrderStatus;
import models.MenuItem;
import models.Order;
import models.Table;
import repo.DbTableRepository;
import repo.MenuItemRepository;
import repo.OrderRepository;
import utils.observer.Observable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MainService extends Observable {

    private final DbTableRepository dbTableRepository;
    private final MenuItemRepository dbMenuItemRepository;
    private final OrderRepository dbOrderRepository;

    public MainService(DbTableRepository dbTableRepository, MenuItemRepository dbMenuItemRepository, OrderRepository dbOrderRepository) {
        this.dbTableRepository = dbTableRepository;
        this.dbMenuItemRepository = dbMenuItemRepository;
        this.dbOrderRepository = dbOrderRepository;
    }

    public List<Table> findAllTables() {
        return dbTableRepository.findAll();
    }

    public Map<String,List<MenuItem>> menuItemsByCategory() {
        List<MenuItem> items = dbMenuItemRepository.findAll();
        return items.stream().collect(
                Collectors.groupingBy(MenuItem::getCategory)
        );
    }

    public List<Order> findPlacedOrders() {
        return (List<Order>) dbOrderRepository.findAll();
    }


    public void placeOrder(Long tableId, List<MenuItem> menuItems) {
        List<Long> menuItemIds = menuItems.stream()
                .map(MenuItem::getId)
                .toList();
        Order order = new Order(tableId,menuItemIds, LocalDateTime.now(), OrderStatus.PLACED);
        dbOrderRepository.save(order);
        notifyObservers(ChangeEvent.ORDER_PLACED);
    }

    public String getItemNamesForOrder(Long orderId) {
        List<Long> menuItemIds = dbOrderRepository.findMenuItemIdsByOrderId(orderId);
        StringBuilder itemNames = new StringBuilder();
        menuItemIds.forEach(menuItemId -> {
            itemNames.append(dbMenuItemRepository.findOne(menuItemId).map(MenuItem::getItem).orElse("")).append(" ");
        });
        return itemNames.toString();
    }
}
