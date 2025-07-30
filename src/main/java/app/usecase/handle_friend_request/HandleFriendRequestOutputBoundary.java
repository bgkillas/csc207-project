package app.usecase.handle_friend_request;

public interface HandleFriendRequestOutputBoundary {
    /**
     * Show success message
     *
     * @param outputData - output message displayed to user
     */
    void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData);

    /**
     * Handle failure and show error message
     *
     * @param errorMessage - error message displayed to user
     */
    void presentFriendRequestFailure(String errorMessage);
}
