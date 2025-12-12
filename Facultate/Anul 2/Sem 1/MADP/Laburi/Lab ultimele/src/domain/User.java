package domain;

import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public abstract class User implements Observer {
    private Long id;
    private final String username;
    private final String email;
    private final String password;
    private final List<Long> friends;
    public User(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        friends=new ArrayList<>();
    }
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        friends=new ArrayList<>();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id=id;
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
    public  void sendMessage(String message){
        System.out.println("User "+this.username+" sent message: "+message);
    }
    public  void receiveMessage(String message){
        System.out.println("User "+this.username+" received message: "+message);
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
        return "User{" +
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
    @Override
    public void update() {
        System.out.println("User "+this.username+" got notified!");
    }


}