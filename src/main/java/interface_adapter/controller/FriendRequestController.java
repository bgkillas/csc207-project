package interface_adapter.controller;

import usecase.team_story.handle_friend_request.HandleFriendRequestInputBoundary;
import entities.User;
import entities.UserSession;

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