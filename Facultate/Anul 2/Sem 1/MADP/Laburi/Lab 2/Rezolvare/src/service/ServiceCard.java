package service;

import domain.Duck;
import domain.Flock;
import domain.FlyingDuck;
import domain.SwimmingDuck;
import enums.DuckType;
import repo.RepoCard;
import repo.RepoUser;
import validation.ValidationStrategy;

import java.util.List;


public class ServiceCard {
    private RepoUser repoUser;
    private RepoCard repoCard;
    private ValidationStrategy<Flock<? extends Duck>> validationStrategy;
    public ServiceCard(RepoUser repoUser, RepoCard repoCard) {
        this.repoUser = repoUser;
        this.repoCard = repoCard;
        this.validationStrategy=new validation.FlockValidationStrategy();
        repoCard.readDataFromFile(repoUser);
    }
    public void addCard(Long cardId, String cardName, String type){
        Flock<? extends Duck> flock;
        if(type.equals("DUCK")){
            flock= new Flock<Duck>(cardId, cardName);
        }else if(type.equals("FLYING")) {
            flock = new Flock<FlyingDuck>(cardId, cardName);
        }else{
            flock=new Flock<SwimmingDuck>(cardId,cardName);
        }
        validationStrategy.validate(flock);
        repoCard.readDataFromFile(repoUser);
        repoCard.addFlock(flock);
        repoCard.writeDataToFile();
    }

    public List<Flock<? extends Duck>> getAll() {
        return repoCard.getAll();
    }

    public Flock<? extends Duck> getFlockById(Long cardId) {
        return repoCard.getFlockById(cardId);
    }
}
