package app.usecase.handle_friend_request;

/**
 * Output boundary interface for friend request use cases. Defines methods for presenting the result
 * of sending, accepting, or declining friend requests to the user interface layer (via presenter).
 */
public interface HandleFriendRequestOutputBoundary {
    /**
     * Show success message.
     *
     * @param outputData - output message displayed to user
     */
    void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData);

    /**
     * Handle failure and show error message.
     *
     * @param errorMessage - error message displayed to user
     */
    void presentFriendRequestFailure(String errorMessage);
}
