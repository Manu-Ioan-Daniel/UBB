package service;

import domain.FriendRequest;
import errors.RepoError;
import errors.ValidationError;
import observer.Observable;
import repo.DatabaseFriendRequestRepository;

import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ServiceFriendRequest extends Observable {
    private final DatabaseFriendRequestRepository frRepo;
    public ServiceFriendRequest(DatabaseFriendRequestRepository frRepo){
        this.frRepo=frRepo;
    }
    public List<FriendRequest> getUserFriendRequests(Long userId){
        return frRepo.getUserFriendRequests(userId);
    }
    public void addFriendRequest(Long fromId, Long toId){
        frRepo.addFriendRequest(fromId, toId);
        notifyObservers();
    }

    public void changeFriendRequestStatus(Long fromId, Long toId, String status){
        if(!Arrays.asList("pending","accepted","denied").contains(status)){
            throw new ValidationError("Invalid status!");
        }
        frRepo.changeFriendRequestStatus(fromId, toId, status);
        notifyObservers();
    }

    public void removeFriendRequest(Long fromId, Long toId){
        frRepo.removeFriendRequest(fromId,toId);
    }

}
