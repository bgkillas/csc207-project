package usecase.team_story.handle_friend_request;

import entities.User;

public interface HandleFriendRequestOutputBoundary {
    /**
     * Show success message
     * @param outputData - output message displayed to user
     */
    void presentFriendRequestSuccess(HandleFriendRequestOutputData outputData);

    /**
     * Handle failure and show error message
     * @param errorMessage - error message displayed to user
     */
    void presentFriendRequestFailure(String errorMessage);
}