package service;
import domain.*;
import enums.DuckType;
import errors.ValidationError;
import observer.Observable;
import repo.DatabaseUserRepository;
import validation.DuckValidationStrategy;
import validation.PersonValidationStrategy;
import validation.ValidationStrategy;

import java.util.ArrayList;
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
    public void removeUser(String username) {
        if(username==null || username.isEmpty()){
            throw new ValidationError("Invalid username!");
        }
        repo.deleteUser(username);
        this.notifyObservers();
    }
    public List<User> getAllUsers(){
        return repo.getAllUsers();
    }
    public void addFriend(String username,String friendUsername) {
        if(username==null || username.isEmpty() || friendUsername==null || friendUsername.isEmpty()){
            throw new ValidationError("Invalid username!");
        }
        repo.addFriend(username,friendUsername);
        this.notifyObservers();
    }
    public void removeFriend(String username, String friendUsername) {
        if(username == null || username.isEmpty() || friendUsername == null || friendUsername.isEmpty()){
            throw new ValidationError("Invalid username!");
        }
        repo.removeFriend(username, friendUsername); // repo modificat să folosească username direct
        this.notifyObservers();
    }

    public User getUserById(Long id){
        return repo.getUserById(id);
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
        if(type==null){
            return getDucks();
        }
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

    public List<Person> getAllPersons() {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user instanceof Person)
                .map(user -> (Person) user)
                .collect(Collectors.toList());
    }
    public List<Duck> getDucksPage(int pageIndex,int pageSize, DuckType type){
        return repo.getDucksPage(pageIndex,pageSize,type);
    }
    public int duckCount(DuckType type){
        return repo.duckCount(type);
    }
    public int personCount(){
        return repo.personCount();
    }
    public List<Person> getPersonsPage(int pageIndex,int pageSize){
        return repo.getPersonsPage(pageIndex,pageSize);
    }
    public List<User> getBiggestCommunity() {
        List<User> users = repo.getAllUsers();
        Map<Long, User> userMap = new HashMap<>();
        for (User u : users) {
            userMap.put(u.getId(), u);
        }

        boolean[] visited = new boolean[users.size()];
        List<User> biggestCommunity = new ArrayList<>();

        for (int i = 0; i < users.size(); i++) {
            if (!visited[i]) {
                List<User> currentCommunity = new ArrayList<>();
                dfsCollect(users, visited, i, userMap, currentCommunity);

                if (currentCommunity.size() > biggestCommunity.size()) {
                    biggestCommunity = currentCommunity;
                }
            }
        }

        return biggestCommunity;
    }
    private void dfsCollect(List<User> users, boolean[] visited, int index,
                            Map<Long, User> userMap, List<User> community) {
        visited[index] = true;
        User user = users.get(index);
        community.add(user);

        for (Long friendID : user.getFriends()) {
            User friend = userMap.get(friendID);
            if (friend != null) {
                int friendIndex = users.indexOf(friend);
                if (!visited[friendIndex]) {
                    dfsCollect(users, visited, friendIndex, userMap, community);
                }
            }
        }
    }


    public String getFriendNames(List<Long> friendIds) {
        StringBuilder friendNames = new StringBuilder();
        for (Long id : friendIds) {
            User friend = repo.getUserById(id);
            if (friend != null) {
                friendNames.append(friend.getUsername()).append(", ");
            }
        }
        if (!friendNames.isEmpty()) {
            friendNames.setLength(friendNames.length() - 2); // Remove trailing comma and space
        }
        return friendNames.toString();
    }
}