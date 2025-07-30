package app.interface_adapter.controller;

import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;
import app.entities.User;
import app.entities.UserSession;

public class FriendRequestController {
    private final HandleFriendRequestInputBoundary handleFriendRequestInteractor;

    public FriendRequestController(HandleFriendRequestInputBoundary handleFriendRequestInteractor) {
        this.handleFriendRequestInteractor = handleFriendRequestInteractor;
    }

    public void acceptRequest(UserSession currentSession, User otherUser) {
        handleFriendRequestInteractor.acceptFriendRequest(currentSession, otherUser);
    }

    public void declineRequest(UserSession currentSession, User otherUser) {
        handleFriendRequestInteractor.declineFriendRequest(currentSession, otherUser);
    }
}