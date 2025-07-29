package usecase.team_story.handle_friend_request;

import entities.User;
import entities.UserSession;

public interface HandleFriendRequestInputBoundary {
    /**
     * Sends friend request to another user.
     *
     * @param userSession the current session.
     * @param toUser the user the request is being sent to.
     */
    void sendFriendRequest(UserSession userSession, User toUser);

    /**
     * Accept friend request from another user.
     *
     * @param userSession the current session.
     * @param fromUser the user the request has been sent from.
     */
    void acceptFriendRequest(UserSession userSession, User fromUser);

    /**
     * Decline friend request from another user.
     *
     * @param userSession the current session.
     * @param fromUser the user the request has been sent from.
     */
    void declineFriendRequest(UserSession userSession, User fromUser);
}
