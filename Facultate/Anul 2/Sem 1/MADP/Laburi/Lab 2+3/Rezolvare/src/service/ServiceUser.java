package service;

import domain.*;
import errors.ServiceError;
import errors.ValidationError;
import repo.DatabaseRepoUser;
import repo.RepoUser;
import validation.DuckValidationStrategy;
import validation.PersonValidationStrategy;
import validation.UserValidationStrategy;
import validation.ValidationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceUser {
    private RepoUser repoUser;
    private DatabaseRepoUser databaseRepoUser;
    private final Map <Class<? extends User>, ValidationStrategy<User>> validators=new HashMap<>();
    public ServiceUser(RepoUser repoUser, DatabaseRepoUser databaseRepoUser) {
        this.repoUser = repoUser;
        this.databaseRepoUser = databaseRepoUser;
        validators.put(Duck.class,new DuckValidationStrategy());
        validators.put(FlyingDuck.class,new DuckValidationStrategy());
        validators.put(SwimmingDuck.class,new DuckValidationStrategy());
        validators.put(Person.class,new PersonValidationStrategy());
        validators.put(User.class,new UserValidationStrategy());
    }
    public void addUser(User user){
        validators.get(user.getClass()).validate(user);
        repoUser.addUser(user);
    }
    public void removeUser(Long id) {
        if(id==null || id<=0){
            throw new ValidationError("Invalid id!");
        }
        repoUser.removeUser(id);
    }
    public List<User> databaseGetAllUsers(){
        return databaseRepoUser.getAll();
    }
    public List<User> getAllUsers(){
        return repoUser.getAllUsers();
    }
    public void addFriend(Long idUser,Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        User u1=getUserById(idUser);
        User u2=getUserById(idFriend);
        if(u1==null || u2==null){
            throw new ServiceError("User not found!");
        }
        u1.addFriend(idFriend);
        u2.addFriend(idUser);
        repoUser.writeDataToFile();
    }
    public void removeFriend(Long idUser, Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        User u1=getUserById(idUser);
        User u2=getUserById(idFriend);
        if(u1==null || u2==null){
            throw new ServiceError("User not found!");
        }
        u1.removeFriend(idFriend);
        u2.removeFriend(idUser);
        repoUser.writeDataToFile();
    }
    public User getUserById(Long id){
        return repoUser.getUserById(id);
    }
    public int getNumberOfCommunities(){
        List<User> users=repoUser.getAllUsers();
        boolean[] visited=new boolean[users.size()];
        int communities=0;
        for(int i=0;i< users.size();i++){
            if(!visited[i]){
                dfs(users,visited,i);
                communities++;
            }
        }
        return communities;
    }
    public int biggestCommunitySize(){
        List<User> users=repoUser.getAllUsers();
        boolean[] visited=new boolean[users.size()];
        int biggestSize=0;
        for(int i=0;i< users.size();i++){
            if(!visited[i]){
                int currentSize=0;
                boolean[] tempVisited=new boolean[users.size()];
                dfs(users,tempVisited,i);
                for(int j=0;j< users.size();j++){
                    if(tempVisited[j]){
                        currentSize++;
                        visited[j]=true;
                    }
                }
                if(currentSize>biggestSize){
                    biggestSize=currentSize;
                }
            }
        }
        return biggestSize;
    }
    private void dfs(List<User> users, boolean[] visited, int index) {
        visited[index] = true;
        User user = users.get(index);
        for (Long friendID : user.getFriends()) {
            int friendIndex = users.indexOf(getUserById(friendID));
            if (!visited[friendIndex]) {
                dfs(users, visited, friendIndex);
            }
        }
    }
}
