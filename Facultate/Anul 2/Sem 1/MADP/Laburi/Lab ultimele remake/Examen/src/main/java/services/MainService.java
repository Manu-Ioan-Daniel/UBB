package services;

import models.Notification;
import java.util.List;

public class MainService {

    private final NotificationService notificationService;

    public MainService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void saveNotification(Notification notification) {
        notificationService.save(notification);
    }

    public void deleteNotification(Long id) {
        notificationService.delete(id);
    }

    public List<Notification> findNotifications(Long toId) {
        return notificationService.findNotifications(toId);
    }

    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    public List<String> getUsernames() {
        return List.of("user1", "user2", "user3");
    }
}
