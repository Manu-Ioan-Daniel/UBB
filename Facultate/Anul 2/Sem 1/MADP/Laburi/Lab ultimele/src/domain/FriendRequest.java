package domain;

public class FriendRequest {
    private final Long from;
    private final Long to;
    private String status; // "pending", "approved", "rejected"
    public FriendRequest(Long from, Long to) {
        this.from = from;
        this.to = to;
        this.status = "pending";
    }
    public FriendRequest(Long from, Long to, String status){
        this.from = from;
        this.to = to;
        this.status = status;
    }
    public Long getFrom() {
        return from;
    }
    public Long getTo() {
        return to;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

}
