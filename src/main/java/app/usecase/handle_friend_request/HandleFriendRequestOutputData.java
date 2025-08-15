package app.usecase.handle_friend_request;

/**
 * Output data model representing the result of handling a friend request. This data is passed from
 * the interactor to the presenter to indicate whether the operation was successful, along with a
 * message and the username of the other user involved in the request.
 */
public class HandleFriendRequestOutputData {
    private final boolean success;
    private final String message;

    /**
     * Constructs an instance of HandleFriendRequestOutputData.
     *
     * @param success true if the operation was successful, false otherwise
     * @param message the message to display to the user
     * @param requesterUsername the username of the user who sent or received the request
     */
    public HandleFriendRequestOutputData(
            boolean success, String message, String requesterUsername) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
