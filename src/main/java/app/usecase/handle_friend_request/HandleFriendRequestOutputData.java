package app.usecase.handle_friend_request;

public class HandleFriendRequestOutputData {
    private final boolean success;
    private final String message;
    private final String requesterUsername;

    public HandleFriendRequestOutputData(boolean success, String message, String requesterUsername) {
        this.success = success;
        this.message = message;
        this.requesterUsername = requesterUsername;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getRequesterUsername() {
        return requesterUsername;
    }
}