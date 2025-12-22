package models;

import domain.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import repo.DbUserRepo;
import utils.database.DbConnection;
import utils.observer.Observable;
import utils.passwords.PasswordHasher;
import validation.UserValidator;
import validation.Validator;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class UserModel extends Observable {
    private final Connection connection;
    private final DbUserRepo userRepo;
    private final Validator<User> userValidator;
    public UserModel(DbUserRepo repo) throws SQLException {
        connection= DbConnection.getInstance().getConnection();
        this.userRepo=repo;
        this.userValidator = new UserValidator();
    }
    public Optional<User> findOne(Long id) {
        return userRepo.findOne(id);
    }

    public Optional<User> findOne(String username) {
        return userRepo.findOne(username);
    }

    public ObservableList<User> findUsersFromPage(int page, int pageSize){
        int offset = (page) * pageSize;
        ObservableList<User> users = FXCollections.observableArrayList();
        for(User user: userRepo.findUsersFromPage(offset, pageSize)){
            users.add(user);
        }
        return users;
    }

    public ObservableList<User> findAll() {
        ObservableList<User> users = FXCollections.observableArrayList();
        for(User user : userRepo.findAll()){
            users.add(user);
        }
        return users;
    }

    public Optional<User> save(User user) {
        return userRepo.save(user);
    }

    public void delete(Long id) {
        Optional<User> user = userRepo.delete(id);
        if(user.isPresent()){
            notifyObservers();
        }
    }

    public Optional<User> update(User user) {
        return userRepo.update(user);
    }
    public boolean validLogin(String username,String password){
        Optional<User> user = findOne(username);
        if(user.isEmpty()){
            return false;
        }
        try {
            return PasswordHasher.verifyPassword(password, user.get().getPassword());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    public void addUser(User user) {
        userValidator.validate(user);
        userRepo.save(user);
        notifyObservers();
    }
    public int getTotalUsers(){
        return userRepo.countUsers();
    }
    public int getTotalDucks(){
        return userRepo.countDucks();
    }
    public int getTotalPeople(){
        return userRepo.countPeople();
    }

}
