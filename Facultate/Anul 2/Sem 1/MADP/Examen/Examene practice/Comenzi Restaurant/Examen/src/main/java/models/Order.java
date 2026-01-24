package models;

import enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

public class Order extends Entity<Long> {
    private final Long tableId;
    private final List<Long> menuItems;
    private final LocalDateTime date;
    private final OrderStatus status;

    public Order(Long tableId, List<Long> menuItems, LocalDateTime date, OrderStatus status) {
        this.tableId = tableId;
        this.menuItems = menuItems;
        this.date = date;
        this.status = status;
    }

    public Long getTableId() {
        return tableId;
    }

    public List<Long> getMenuItems() {
        return menuItems;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public OrderStatus getStatus() {
        return status;
    }
}
