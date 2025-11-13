package service;

import domain.*;
import enums.DuckType;
import repo.DatabaseRepoCard;
import repo.DatabaseRepoUser;
import repo.RepoCard;
import repo.RepoUser;
import validation.ValidationStrategy;

import java.util.List;


public class ServiceCard {
    private RepoUser repoUser;
    private RepoCard repoCard;
    private DatabaseRepoCard databaseRepoCard;
    private DatabaseRepoUser databaseRepoUser;
    private ValidationStrategy<Flock<Duck>> validationStrategy;
    public ServiceCard(RepoUser repoUser, RepoCard repoCard, DatabaseRepoCard databaseRepoCard, DatabaseRepoUser databaseRepoUser) {
        this.repoUser = repoUser;
        this.repoCard = repoCard;
        this.validationStrategy=new validation.FlockValidationStrategy();
        this.databaseRepoCard=databaseRepoCard;
        this.databaseRepoUser=databaseRepoUser;
        repoCard.readDataFromFile(repoUser);
    }
    public void addCard(Long cardId, String cardName, String type){
        Flock<Duck> flock = new Flock<Duck>(cardId, cardName);
        validationStrategy.validate(flock);
        repoCard.readDataFromFile(repoUser);
        repoCard.addFlock(flock);
        repoCard.writeDataToFile();
    }

    public List<Flock<Duck>> getAll() {
        return repoCard.getAll();
    }
    public List<Flock<Duck>> databaseGetAll() {
        List<Flock<Duck>> flocks=databaseRepoCard.getAll();
        List<User> users=databaseRepoUser.getAll();
        for(Flock<Duck> flock:flocks){
            for(User user:users){
                if(user instanceof Duck duck){
                    if(duck.getFlock()!=null && duck.getFlock().getId().equals(flock.getId())){
                        flock.addDuck(duck);
                        duck.setFlock(flock);
                    }
                }
            }
        }
        return flocks;
    }
    public Flock<Duck> getFlockById(Long cardId) {
        return repoCard.getFlockById(cardId);
    }
}
