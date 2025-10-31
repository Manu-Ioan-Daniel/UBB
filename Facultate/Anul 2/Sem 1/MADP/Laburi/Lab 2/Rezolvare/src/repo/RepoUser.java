package repo;
import domain.Duck;
import domain.Person;
import domain.User;
import enums.DuckType;
import errors.RepoError;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RepoUser {
    private List<User> users;
    private String dataFile;
    public RepoUser() {
        users=new ArrayList<>();
    }
    public RepoUser(String dataFile) {
        this.dataFile = dataFile;
        users=new ArrayList<>();
        readDataFromFile();
    }
    public void addUser(User user){
        readDataFromFile();
        if(users.contains(user))
            throw new RepoError("User already exists");
        users.add(user);
        writeDataToFile();
    }
    public void removeUser(Long id) {
        readDataFromFile();
        for(User user:users){
            if(user.getId().equals(id)){
                users.remove(user);
                writeDataToFile();
                return;
            }
        }
        throw new RepoError("User does not exist!");
    }
    public void readDataFromFile() {
        if (dataFile == null) return;
        users.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                int friendStartIndex=0;
                User user;
                String[] parts = line.split(",", -1); // -1 pastreaza campuri goale
                String type = parts[0];
                Long id = Long.parseLong(parts[1]);
                String username = parts[2];
                String email = parts[3];
                String password = parts[4];
                if (type.equals("person")) {
                    String name = parts[5];
                    String surname = parts[6];
                    String dob = parts[7];
                    String occupation = parts[8];
                    int empathy = Integer.parseInt(parts[9]);
                    user=new Person(id, username, email, password, name, surname, dob, occupation, empathy);
                    friendStartIndex = 10;
                } else if (type.equals("duck")) {
                    DuckType duckType = DuckType.valueOf(parts[5]);
                    double speed = Double.parseDouble(parts[6]);
                    double rez = Double.parseDouble(parts[7]);
                    user = new Duck(id, username, email, password, duckType, speed, rez, null);
                    friendStartIndex = 8;
                }else{
                    throw new RepoError("Unknown user type: " + type);
                }
                //verific daca userul are prieteni
                if (parts.length > friendStartIndex) {
                    for (int i = friendStartIndex; i < parts.length; i++) {
                        if (!parts[i].isEmpty()) {
                            Long friendId = Long.parseLong(parts[i]);
                            user.addFriend(friendId);
                        }
                    }
                }
                users.add(user);
            }

        } catch (IOException e) {
            throw new RepoError("Failed to read data from file: " + e.getMessage());
        }
    }
    public void writeDataToFile() {
        if (dataFile == null) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile,false))) {
            for (User u : users) {
                StringBuilder sb = new StringBuilder();
                if (u instanceof Person p) {
                    sb.append("person").append(",");
                    sb.append(p.getId()).append(",");
                    sb.append(p.getUsername()).append(",");
                    sb.append(p.getEmail()).append(",");
                    sb.append(p.getPassword()).append(",");
                    sb.append(p.getName()).append(",");
                    sb.append(p.getSurname()).append(",");
                    sb.append(p.getDateOfBirth()).append(",");
                    sb.append(p.getOccupation()).append(",");
                    sb.append(p.getEmpathyScore());
                } else if (u instanceof Duck d) {
                    sb.append("duck").append(",");
                    sb.append(d.getId()).append(",");
                    sb.append(d.getUsername()).append(",");
                    sb.append(d.getEmail()).append(",");
                    sb.append(d.getPassword()).append(",");
                    sb.append(d.getType()).append(",");
                    sb.append(d.getSpeed()).append(",");
                    sb.append(d.getRezistance());
                }

                for (Long friendId : u.getFriends()) {
                    sb.append(",").append(friendId);
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RepoError("Failed to write data to file: " + e.getMessage());
        }
    }
    public List<User> getAllUsers(){
        return Collections.unmodifiableList(users);
    }
}
