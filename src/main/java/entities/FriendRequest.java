package entities;

public class FriendRequest {
    private final User fromUser;
    private final User toUser;
    private final int compatibilityScore;
    private String status;

    public FriendRequest(User fromUser, User toUser, int compatibilityScore) {
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.compatibilityScore = compatibilityScore;
        this.status = "PENDING";
    }

    public void accept() {
        this.status = "ACCEPTED";
    }

    public void decline() {
        this.status = "DECLINED";
    }

    public User getFromUser() {
        return fromUser;
    }

    public User getToUser() {
        return toUser;
    }

    public int getCompatibilityScore() {
        return compatibilityScore;
    }
}
