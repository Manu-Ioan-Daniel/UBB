package service;
import domain.*;
import errors.ValidationError;
import repo.DatabaseUserRepository;
import validation.DuckValidationStrategy;
import validation.PersonValidationStrategy;
import validation.ValidationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceUser {
    private final DatabaseUserRepository repo;
    private final Map <Class<? extends User>, ValidationStrategy<? extends User>> validators=new HashMap<>();
    public ServiceUser(DatabaseUserRepository repo) {
        this.repo=repo;
        validators.put(Duck.class,new DuckValidationStrategy());
        validators.put(Person.class,new PersonValidationStrategy());
    }
    @SuppressWarnings("unchecked")
    public void addUser(User user){
        ValidationStrategy<User> validator=(ValidationStrategy<User>) validators.get(user.getClass());
        validator.validate(user);
        repo.addUser(user);
    }
    public void removeUser(Long id) {
        if(id==null || id<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.deleteUser(id);
    }
    public List<User> getUsers(){
        return repo.getUsers();
    }
    public void addFriend(Long idUser,Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.addFriend(idUser,idFriend);
    }
    public void removeFriend(Long idUser, Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.removeFriend(idUser,idFriend);
    }
    public User getUserById(Long id){
        if(id==null || id<=0){
            throw new ValidationError("Invalid id!");
        }
        for(User user:repo.getUsers()){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    public int getNumberOfCommunities() {
        List<User> users = repo.getUsers();
        Map<Long, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getId(), u);
        }
        boolean[] visited = new boolean[users.size()];
        int communities = 0;
        for (int i = 0; i < users.size(); i++) {
            if (!visited[i]) {
                dfs(users, visited, i, userMap);
                communities++;
            }
        }
        return communities;
    }

    public int biggestCommunitySize() {
        List<User> users = repo.getUsers();
        Map<Long, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getId(), u);
        }

        boolean[] visited = new boolean[users.size()];
        int biggestSize = 0;

        for (int i = 0; i < users.size(); i++) {
            if (!visited[i]) {
                boolean[] tempVisited = new boolean[users.size()];
                dfs(users, tempVisited, i, userMap);

                int currentSize = 0;
                for (int j = 0; j < users.size(); j++) {
                    if (tempVisited[j]) {
                        currentSize++;
                        visited[j] = true;
                    }
                }

                if (currentSize > biggestSize) {
                    biggestSize = currentSize;
                }
            }
        }
        return biggestSize;
    }
    private void dfs(List<User> users, boolean[] visited, int index, Map<Long, User> userMap) {
        visited[index] = true;
        User user = users.get(index);
        for (Long friendID : user.getFriends()) {
            User friend = userMap.get(friendID);
            if (friend != null) {
                int friendIndex = users.indexOf(friend);
                if (!visited[friendIndex]) {
                    dfs(users, visited, friendIndex, userMap);
                }
            }
        }
    }
}