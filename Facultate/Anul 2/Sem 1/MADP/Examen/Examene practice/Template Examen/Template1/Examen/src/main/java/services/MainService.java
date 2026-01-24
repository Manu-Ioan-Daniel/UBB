package services;

import enums.ChangeEvent;
import exceptions.ServiceException;
import models.Notification;
import repo.NotificationRepo;
import utils.observer.Observable;
import validator.Validator;

import java.util.List;

public class MainService extends Observable {

    private final NotificationRepo repo;
    private final Validator<Notification> validator;

    public MainService(NotificationRepo repo, Validator<Notification> validator) {
        this.repo = repo;
        this.validator = validator;
    }

    public void save(Notification notification) {
        if(repo.findOne(notification.getId()).isPresent()) {
            throw new ServiceException("Notification already exists");
        }
        repo.save(notification);
        notifyObservers(ChangeEvent.NOTIFICATION);
    }

    public void delete(Long id) {
        repo.delete(id);
        notifyObservers(ChangeEvent.NOTIFICATION);
    }

    public List<Notification> findNotifications(Long toId) {
        return repo.findNotifications(toId);
    }

    public List<Notification> getAllNotifications() {
        return repo.findAll();
    }
}
