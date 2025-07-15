package usecase.teamStory;

import entities.User;
import entities.UserSession;

public interface HandleFriendRequest {
    void sendFriendRequest(UserSession userSession, User toUser);

    void acceptFriendRequest(UserSession userSession, User fromUser);

    void declineFriendRequest(UserSession userSession, User fromUser);
}
