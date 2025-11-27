package service;
import domain.*;
import enums.DuckType;
import errors.ValidationError;
import observer.Observable;
import repo.DatabaseUserRepository;
import validation.DuckValidationStrategy;
import validation.PersonValidationStrategy;
import validation.ValidationStrategy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ServiceUser extends Observable {
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
        this.notifyObservers();
    }
    public void removeUser(Long id) {
        if(id==null || id<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.deleteUser(id);
        this.notifyObservers();
    }
    public List<User> getAllUsers(){
        return repo.getAllUsers();
    }
    public void addFriend(Long idUser,Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.addFriend(idUser,idFriend);
        this.notifyObservers();
    }
    public void removeFriend(Long idUser, Long idFriend) {
        if(idUser==null || idUser<=0 || idFriend==null || idFriend<=0){
            throw new ValidationError("Invalid id!");
        }
        repo.removeFriend(idUser,idFriend);
        this.notifyObservers();
    }
    public User getUserById(Long id){
        if(id==null || id<=0){
            throw new ValidationError("Invalid id!");
        }
        for(User user:repo.getAllUsers()){
            if(user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    public int getNumberOfCommunities() {
        List<User> users = repo.getAllUsers();
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

    public int getBiggestCommunitySize() {
        List<User> users = repo.getAllUsers();
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
    public List<Duck> getDucksByType(DuckType type){
        List<User> users=getAllUsers();
        return users.stream()
                .filter(user -> user instanceof Duck)
                .map(user -> (Duck) user)
                .filter(duck-> duck.getType().equals(type))
                .collect(Collectors.toList());

    }
    public List<Duck> getDucks(){
        List<User> users=getAllUsers();
        return users.stream()
                .filter(user -> user instanceof Duck)
                .map(user -> (Duck) user)
                .collect(Collectors.toList());
    }
}