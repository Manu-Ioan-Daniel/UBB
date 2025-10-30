package domain;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private final Long id;
    private final String username;
    private final String email;
    private final String password;
    private List<Long> friends;
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        friends=new ArrayList<>();
    }
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void login(){
        return;
    }
    public void logout(){
        return;
    }
    public  void sendMessage(){
        return;
    }
    public  void receiveMessage(){
        return;
    }
    public List<Long> getFriends() {
        return friends;
    }
    public void addFriend(Long id) {
        this.friends.add(id);
    }
    public void removeFriend(Long id) {
        this.friends.remove(id);
    }
    @Override
    public String toString() {
        return "domain.User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", friends=" + friends.size() +
                '}';
    }
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof User other){
            return this.id.equals(other.id);
        }
        return false;
    }
    @Override
    public int hashCode() {
        return id.hashCode();
    }


}
