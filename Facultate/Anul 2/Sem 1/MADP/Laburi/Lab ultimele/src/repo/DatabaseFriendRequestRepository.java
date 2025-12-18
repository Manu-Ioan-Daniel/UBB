package repo;
import domain.FriendRequest;
import errors.RepoError;

import java.sql.*;
import java.util.List;

public class DatabaseFriendRequestRepository {
    private final Connection connection;
    public DatabaseFriendRequestRepository(String databaseUrl) {
        try{
            this.connection = DriverManager.getConnection(databaseUrl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<FriendRequest> getUserFriendRequests(Long userId) {
        String sql = """
                        SELECT fromId, toId, status
                        FROM friendRequests
                        WHERE toId = ?
                        """;
        try{
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            List<FriendRequest> requests = new java.util.ArrayList<>();
            while(rs.next()) {
                Long fromId = rs.getLong(1);
                Long toId = rs.getLong(2);
                String status = rs.getString(3);
                FriendRequest fr = new FriendRequest(fromId,toId,status);
                requests.add(fr);
            }
            rs.close();
            ps.close();
            return requests;
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

    }

    public void addFriendRequest(Long fromId,Long toId){
        if(friendRequestExists(fromId, toId) || friendRequestExists(toId, fromId) ){
            throw new RepoError("You already sent/received a request to/from this user");
        }
        if(fromId.equals(toId)){
            throw new RepoError("You cannot friend request yourself!");
        }
        if(friendshipExists(fromId,toId)){
            throw new RepoError("You are already friends with this user");
        }
        String sql = """
                INSERT INTO friendrequests (fromId,toId,status) VALUES (?, ?, 'pending')
                """;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,fromId);
            ps.setLong(2,toId);
            ps.executeUpdate();
            ps.close();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    public void changeFriendRequestStatus(Long fromId, Long toId, String status){
        String sql = """
                UPDATE friendrequests
                SET status = ?
                WHERE fromId = ? AND toId = ?
                """;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1,status);
            ps.setLong(2,fromId);
            ps.setLong(3,toId);
            ps.executeUpdate();

        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    private boolean friendRequestExists(Long fromId, Long toId) {
        String sql= """
                SELECT 1
                FROM friendrequests
                WHERE fromId = ? AND toId = ? AND status = 'pending'
                """;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,fromId);
            ps.setLong(2,toId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    private boolean friendshipExists(Long fromId, Long toId){
        String sql = """
                SELECT 1
                FROM userfriends
                WHERE userId = ? AND friendId = ?
                """;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,fromId);
            ps.setLong(2,toId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void removeFriendRequest(Long fromId,Long toId){
        if(!friendshipExists(fromId,toId)){
            throw new RepoError("Nu exista cerere de prietenie intre userii respectivi");
        }
        String sql = """
                DELETE
                FROM userfriends
                WHERE fromId = ? AND toId = ?
                """;
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1,fromId);
            ps.setLong(2,toId);
            ps.executeUpdate();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}
