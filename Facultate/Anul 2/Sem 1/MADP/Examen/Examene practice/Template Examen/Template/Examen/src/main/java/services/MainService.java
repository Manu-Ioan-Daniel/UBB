package services;
import models.User;
import repo.UserRepo;
import utils.Tuple;
import utils.observer.Observable;

public class MainService extends Observable {

    private final UserRepo userRepo;

        public MainService(UserRepo userRepo) {
            this.userRepo = userRepo;
        }

    public Tuple<Boolean,String> validLogin(String username, String password){
        User user = userRepo.findByUsername(username);
        if(user != null && user.getPassword().equals(password)){
            return user instanceof models.Admin ? new Tuple<>(true,"admin") : new Tuple<>(true,"normal");
        }
        return new Tuple<>(false,"");
    }
}
