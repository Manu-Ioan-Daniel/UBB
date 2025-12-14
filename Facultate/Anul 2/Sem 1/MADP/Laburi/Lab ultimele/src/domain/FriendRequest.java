package domain;

public class FriendRequest {
    private final User from;
    private final User to;
    private String status; // "pending", "approved", "rejected"
    public FriendRequest(User from, User to) {
        this.from = from;
        this.to = to;
        this.status = "pending";
    }
    public User getFrom() {
        return from;
    }
    public User getTo() {
        return to;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
