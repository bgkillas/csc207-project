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

        // Basic guards
        if (currentUser == null || matchedUser == null) {
            presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                    false, false, matchedUser == null ? "" : matchedUser.getName(),
                    "Cannot connect right now."));
            return;
        }
        if (currentUser.equals(matchedUser)) {
            presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                    false, false, matchedUser.getName(),
                    "You can't connect with yourself."));
            return;
        }
        if (currentUser.getFriendList().contains(matchedUser)) {
            presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                    true, true, matchedUser.getName(),
                    "You’re already friends with " + matchedUser.getName()));
            return;
        }

        // Has the current user already sent a request to matchedUser?
        boolean alreadyOutgoing =
                userSession.getOutgoingMatches().contains(matchedUser) ||
                        matchDataAccessObject.getOutgoingFriendRequest(currentUser).contains(matchedUser);

        if (alreadyOutgoing) {
            presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                    true, false, matchedUser.getName(),
                    "You’ve already sent a request to " + matchedUser.getName()));
            return;
        }

        // Did matchedUser already send a request to currentUser? => mutual connect
        boolean mutualConnect =
                userSession.getIncomingMatches().contains(matchedUser) ||
                        matchDataAccessObject.getOutgoingFriendRequest(matchedUser).contains(currentUser);

        if (mutualConnect) {
            // Clean up pending requests on both sides
            matchDataAccessObject.getOutgoingFriendRequest(matchedUser).remove(currentUser);
            matchDataAccessObject.getIncomingFriendRequest(currentUser).remove(matchedUser);

            userSession.getIncomingMatches().remove(matchedUser);
            userSession.getOutgoingMatches().remove(matchedUser);

            // Become friends
            addFriendList.addFriend(currentUser, matchedUser);

            presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                    true, true, matchedUser.getName(),
                    "You are now friends with " + matchedUser.getName()));
            return;
        }

        // Otherwise: create a fresh friend request (single source of truth)
        matchDataAccessObject.addOutgoingFriendRequest(currentUser, matchedUser);
        matchDataAccessObject.addIncomingFriendRequest(matchedUser, currentUser);

        // Update the current user's local session for UI (outgoing list only)
        if (!userSession.getOutgoingMatches().contains(matchedUser)) {
            userSession.getOutgoingMatches().add(matchedUser);
        }

        presenter.presentMatchInteractionResult(new MatchInteractionOutputData(
                true, false, matchedUser.getName(),
                "Friend request sent to " + matchedUser.getName()));
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
