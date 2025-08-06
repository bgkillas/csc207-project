package app.usecase.match_interaction;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.MatchDataAccessInterface;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;

/**
 * Interactor for handling match interactions such as connecting with or skipping matched users.
 * Implements the MatchInteractionInputBoundary interface.
 */
public class MatchInteractionInteractor implements MatchInteractionInputBoundary {

    private final MatchDataAccessInterface matchDataAccessObject;
    private final HandleFriendRequestInputBoundary handleFriendRequest;
    private final AddFriendListInputBoundary addFriendList;
    private final MatchInteractionOutputBoundary presenter;

    /**
     * Constructs a MatchInteractionInteractor.
     *
     * @param matchDataAccessObject interface for accessing and modifying match-related data
     * @param handleFriendRequest input boundary for sending/handling friend requests
     * @param addFriendList input boundary for adding users to the friend list
     * @param presenter output boundary for presenting match interaction results
     */
    public MatchInteractionInteractor(
            MatchDataAccessInterface matchDataAccessObject,
            HandleFriendRequestInputBoundary handleFriendRequest,
            AddFriendListInputBoundary addFriendList,
            MatchInteractionOutputBoundary presenter) {
        this.matchDataAccessObject = matchDataAccessObject;
        this.handleFriendRequest = handleFriendRequest;
        this.addFriendList = addFriendList;
        this.presenter = presenter;
    }

    /**
     * Called when the current user chooses to connect with a matched user. If the matched user has
     * already sent a request, they become friends. Otherwise, a friend request is sent to the
     * matched user.
     *
     * @param userSession the current user's session
     * @param matchedUser the user to connect with
     */
    @Override
    public void connect(UserSession userSession, User matchedUser) {
        User currentUser = userSession.getUser();

        boolean mutualConnect =
                matchDataAccessObject.getOutgoingFriendRequest(matchedUser).contains(currentUser);

        if (mutualConnect) {
            addFriendList.addFriend(currentUser, matchedUser);
            matchDataAccessObject.getOutgoingFriendRequest(matchedUser).remove(currentUser);
            matchDataAccessObject.getIncomingFriendRequest(currentUser).remove(matchedUser);

            presenter.presentMatchInteractionResult(
                    new MatchInteractionOutputData(
                            true,
                            true,
                            matchedUser.getName(),
                            "You are now friends with " + matchedUser.getName()));
        } else {
            handleFriendRequest.sendFriendRequest(userSession, matchedUser);

            presenter.presentMatchInteractionResult(
                    new MatchInteractionOutputData(
                            true,
                            false,
                            matchedUser.getName(),
                            "Friend request sent to " + matchedUser.getName()));
        }
    }

    /**
     * Called when the current user chooses to skip a matched user. The matched user is removed from
     * both incoming and outgoing match lists.
     *
     * @param userSession the current user's session
     * @param matchedUser the user to skip
     */
    @Override
    public void skip(UserSession userSession, User matchedUser) {
        userSession.getIncomingMatches().remove(matchedUser);
        userSession.getOutgoingMatches().remove(matchedUser);
    }
}
