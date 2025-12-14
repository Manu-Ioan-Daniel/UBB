package service;

import domain.*;
import enums.DuckType;
import errors.ValidationError;
import observer.Observable;
import repo.DatabaseUserRepository;
import utils.PasswordUtils;
import validation.DuckValidationStrategy;
import validation.PersonValidationStrategy;
import validation.ValidationStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class ServiceUser extends Observable {

    private final DatabaseUserRepository repo;
    private final Map<Class<? extends User>, ValidationStrategy<? extends User>> validators = new HashMap<>();

    public ServiceUser(DatabaseUserRepository repo) {
        this.repo = repo;
        validators.put(Duck.class, new DuckValidationStrategy());
        validators.put(Person.class, new PersonValidationStrategy());
    }

    @SuppressWarnings("unchecked")
    public void addUser(User user) {
        ValidationStrategy<User> validator = (ValidationStrategy<User>) validators.get(user.getClass());
        validator.validate(user);
        repo.addUser(user);
        this.notifyObservers();
    }

    public void removeUser(String username) {
        if (username == null || username.isEmpty()) {
            throw new ValidationError("Invalid username!");
        }
        repo.deleteUser(username);
        this.notifyObservers();
    }

    public List<User> getAllUsers() {
        return repo.getAllUsers();
    }

    public void addFriend(String username, String friendUsername) {
        if (username == null || username.isEmpty() || friendUsername == null || friendUsername.isEmpty()) {
            throw new ValidationError("Invalid username!");
        }
        repo.addFriend(username, friendUsername);
        this.notifyObservers();
    }

    public void removeFriend(String username, String friendUsername) {
        if (username == null || username.isEmpty() || friendUsername == null || friendUsername.isEmpty()) {
            throw new ValidationError("Invalid username!");
        }
        repo.removeFriend(username, friendUsername);
        this.notifyObservers();
    }

    public User getUserById(Long id) {
        return repo.getUserById(id);
    }

    // ------------------ Communities ------------------

    public int getNumberOfCommunities() {
        List<User> users = repo.getAllUsers();
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
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
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
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

    public List<User> getBiggestCommunity() {
        List<User> users = repo.getAllUsers();
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(User::getId, u -> u));
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

    // ------------------ Ducks ------------------


    public List<Duck> getDucksPage(int pageIndex, int pageSize, DuckType type) {
        return repo.getDucksPage(pageIndex, pageSize, type);
    }

    public int duckCount(DuckType type) {
        return repo.duckCount(type);
    }

    // ------------------ Persons ------------------

    public List<Person> getPersonsPage(int pageIndex, int pageSize) {
        return repo.getPersonsPage(pageIndex, pageSize);
    }

    public int personCount() {
        return repo.personCount();
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
            friendNames.setLength(friendNames.length() - 2); // remove trailing comma
        }
        return friendNames.toString();
    }
    public User getUserByUsername(String username) {
        return repo.getUserByUsername(username);
    }
    public boolean authenticateUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            return PasswordUtils.verifyPassword(password,user.getPassword());
        }
        return false;
    }


}
