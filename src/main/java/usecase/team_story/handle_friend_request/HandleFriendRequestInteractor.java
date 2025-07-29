package usecase.team_story.handle_friend_request;

import data_access.MatchDataAccessInterface;
import entities.User;
import entities.UserSession;
import usecase.team_story.handle_friend_request.HandleFriendRequestOutputBoundary;
import usecase.team_story.handle_friend_request.HandleFriendRequestOutputData;
import usecase.team_story.add_friend_list.AddFriendListInputBoundary;

public class HandleFriendRequestInteractor implements HandleFriendRequestInputBoundary {
    private final MatchDataAccessInterface matchDAO;
    private final AddFriendListInputBoundary addFriendListInteractor;
    private final HandleFriendRequestOutputBoundary presenter;

    public HandleFriendRequestInteractor(
            MatchDataAccessInterface matchDAO,
            AddFriendListInputBoundary addFriendListInteractor,
            HandleFriendRequestOutputBoundary presenter) {
        this.matchDAO = matchDAO;
        this.addFriendListInteractor = addFriendListInteractor;
        this.presenter = presenter;
    }

    @Override
    public void sendFriendRequest(UserSession userSession, User toUser) {
        User currentUser = userSession.getUser();

        if (toUser == null) {
            presenter.presentFriendRequestFailure("Cannot send friend request: user does not exist.");
            return;
        }
        if (toUser.equals(currentUser)) {
            presenter.presentFriendRequestFailure("Cannot send friend request to yourself.");
            return;
        }
        if (currentUser.getFriendList().contains(toUser) ||
                userSession.getOutgoingMatches().contains(toUser)) {
            presenter.presentFriendRequestFailure("Already friends or request already sent.");
            return;
        }

        matchDAO.addOutgoingFriendRequest(currentUser, toUser);
        userSession.getOutgoingMatches().add(toUser);

        matchDAO.addIncomingFriendRequest(toUser, currentUser);
        userSession.getIncomingMatches().add(currentUser);

        HandleFriendRequestOutputData outputData = new HandleFriendRequestOutputData(
                true,
                "Friend request sent to " + toUser.getName(),
                toUser.getName()
        );
        presenter.presentFriendRequestSuccess(outputData);
    }

    @Override
    public void acceptFriendRequest(UserSession userSession, User fromUser) {
        User currentUser = userSession.getUser();

        if (fromUser == null) {
            presenter.presentFriendRequestFailure("Cannot accept friend request: user does not exist.");
            return;
        }
        if (!userSession.getIncomingMatches().contains(fromUser)) {
            presenter.presentFriendRequestFailure("Friend request does not exist.");
            return;
        }

        matchDAO.getIncomingFriendRequest(currentUser).remove(fromUser);
        userSession.getIncomingMatches().remove(fromUser);
        matchDAO.getOutgoingFriendRequest(fromUser).remove(currentUser);

        addFriendListInteractor.addFriend(currentUser, fromUser);

        HandleFriendRequestOutputData outputData = new HandleFriendRequestOutputData(
                true,
                "You are now friends with " + fromUser.getName(),
                fromUser.getName()
        );
        presenter.presentFriendRequestSuccess(outputData);
    }

    @Override
    public void declineFriendRequest(UserSession userSession, User fromUser) {
        User currentUser = userSession.getUser();

        if (fromUser == null) {
            presenter.presentFriendRequestFailure("Cannot decline friend request: user does not exist.");
            return;
        }
        if (!userSession.getIncomingMatches().contains(fromUser)) {
            presenter.presentFriendRequestFailure("Friend request does not exist.");
            return;
        }

        matchDAO.getIncomingFriendRequest(currentUser).remove(fromUser);
        userSession.getIncomingMatches().remove(fromUser);
        matchDAO.getOutgoingFriendRequest(fromUser).remove(currentUser);

        HandleFriendRequestOutputData outputData = new HandleFriendRequestOutputData(
                true,
                "Declined friend request from " + fromUser.getName(),
                fromUser.getName()
        );
        presenter.presentFriendRequestSuccess(outputData);
    }
}
