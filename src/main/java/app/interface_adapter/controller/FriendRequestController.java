package app.interface_adapter.controller;

import app.entities.User;
import app.entities.UserSession;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;

/**
 * Controller for handling incoming friend requests.
 * This class receives user actions (accepting or declining requests)
 * and delegates them to the HandleFriendRequestInputBoundary interactor.
 */
public class FriendRequestController {
    private final HandleFriendRequestInputBoundary handleFriendRequestInteractor;

    /**
     * Constructs a new FriendRequestController.
     *
     * @param handleFriendRequestInteractor the interactor responsible for handling friend requests
     */
    public FriendRequestController(HandleFriendRequestInputBoundary handleFriendRequestInteractor) {
        this.handleFriendRequestInteractor = handleFriendRequestInteractor;
    }

    /**
     * Accepts a friend request from another user.
     *
     * @param currentSession the session of the user who is accepting the request
     * @param otherUser      the user who sent the friend request
     */
    public void acceptRequest(UserSession currentSession, User otherUser) {
        handleFriendRequestInteractor.acceptFriendRequest(currentSession, otherUser);
    }

    /**
     * Declines a friend request from another user.
     *
     * @param currentSession the session of the user who is declining the request
     * @param otherUser      the user who sent the friend request
     */
    public void declineRequest(UserSession currentSession, User otherUser) {
        handleFriendRequestInteractor.declineFriendRequest(currentSession, otherUser);
    }
}
