package app.teamStory;

import data_access.MatchDataAccessInterface;
import entities.User;
import entities.UserSession;
import usecase.teamStory.AddFriendsList;
import usecase.teamStory.HandleFriendRequest;

public class HandleFriendRequestImpl implements HandleFriendRequest {
    private final MatchDataAccessInterface matchDAO;

    /**
     * Creates use case for handling friend request.
     * @param matchDAO data access object for matches.
     */
    public HandleFriendRequestImpl(MatchDataAccessInterface matchDAO) {
        this.matchDAO = matchDAO;
    }

    /**
     * Sends friend request to another user.
     * @param userSession the current session.
     * @param toUser the user the request is being sent to.
     */
    @Override
    public void sendFriendRequest(UserSession userSession, User toUser) {
        User currentUser = userSession.getUser();

        // add user to outgoing
        matchDAO.addOutgoingFriendRequest(userSession.getUser(), toUser);    // Repo updates
        userSession.getOutgoingMatches().add(toUser);                        // Session updates
        // add user to incoming
        matchDAO.addIncomingFriendRequest(toUser, currentUser);              // Repo updates
        userSession.getIncomingMatches().add(currentUser);                   // Session updates
    }

    @Override
    public void acceptFriendRequest(UserSession userSession, User fromUser) {
        User currentUser = userSession.getUser();

        // remove user from incoming
        matchDAO.getIncomingFriendRequest(currentUser).remove(fromUser);    // Repo updates
        userSession.getIncomingMatches().remove(fromUser);                  // Session updates

        // Remove from fromUser's outgoing
        matchDAO.getOutgoingFriendRequest(fromUser).remove(currentUser);

        AddFriendsList addFriendsListImpl = new AddFriendsListImpl();
        addFriendsListImpl.addFriend(userSession.getUser(), fromUser);
    }

    @Override
    public void declineFriendRequest(UserSession userSession, User fromUser) {
        User currentUser = userSession.getUser();

        // remove user from incoming
        matchDAO.getIncomingFriendRequest(currentUser).remove(fromUser);    // Repo updates
        userSession.getIncomingMatches().remove(fromUser);                  // Session updates

        // Remove from fromUser's outgoing
        matchDAO.getOutgoingFriendRequest(fromUser).remove(currentUser);
    }
}
