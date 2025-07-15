package app.teamStory;

import entities.User;
import entities.UserSession;
import usecase.teamStory.AddFriendsList;
import usecase.teamStory.HandleFriendRequest;

public class HandleFriendRequestImpl implements HandleFriendRequest {
    @Override
    public void sendFriendRequest(UserSession userSession, User toUser) {
        userSession.getOutgoingMatches().add(toUser);
        System.out.println("Friend request sent to " + toUser.getName());
    }

    @Override
    public void acceptFriendRequest(UserSession userSession, User fromUser) {
        // remove user from incoming
        userSession.getIncomingMatches().remove(fromUser);

        AddFriendsList addFriendsListImp = new AddFriendsListImpl();
        addFriendsListImp.addFriend(userSession.getUser(), fromUser);
        System.out.println("Friend request accepted from " + fromUser.getName());
    }

    @Override
    public void declineFriendRequest(UserSession userSession, User fromUser) {
        userSession.getIncomingMatches().remove(fromUser);
        System.out.println("Friend request declined to " + fromUser.getName());
    }
}
