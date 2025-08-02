package app.usecase.match_interaction;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.data_access.MatchDataAccessInterface;
import app.usecase.add_friend_list.AddFriendListInputBoundary;
import app.usecase.handle_friend_request.HandleFriendRequestInputBoundary;

public class MatchInteractionInteractor implements MatchInteractionInputBoundary {

    private final MatchDataAccessInterface matchDAO;
    private final HandleFriendRequestInputBoundary handleFriendRequest;
    private final AddFriendListInputBoundary addFriendList;
    private final MatchInteractionOutputBoundary presenter;

    public MatchInteractionInteractor(
            MatchDataAccessInterface matchDAO,
            HandleFriendRequestInputBoundary handleFriendRequest,
            AddFriendListInputBoundary addFriendList,
            MatchInteractionOutputBoundary presenter) {
        this.matchDAO = matchDAO;
        this.handleFriendRequest = handleFriendRequest;
        this.addFriendList = addFriendList;
        this.presenter = presenter;
    }


    @Override
    public void connect(UserSession userSession, User matchedUser) {
        User currentUser = userSession.getUser();

        boolean mutualConnect =
                matchDAO.getOutgoingFriendRequest(matchedUser).contains(currentUser);

        if (mutualConnect) {
            // 双向 connect → 成为好友
            addFriendList.addFriend(currentUser, matchedUser);
            matchDAO.getOutgoingFriendRequest(matchedUser).remove(currentUser);
            matchDAO.getIncomingFriendRequest(currentUser).remove(matchedUser);

            presenter.presentMatchInteractionResult(
                    new MatchInteractionOutputData(
                            true,
                            true,
                            matchedUser.getName(),
                            "You are now friends with " + matchedUser.getName()));
        } else {
            // 单向 connect → 发起好友请求
            handleFriendRequest.sendFriendRequest(userSession, matchedUser);

            presenter.presentMatchInteractionResult(
                    new MatchInteractionOutputData(
                            true,
                            false,
                            matchedUser.getName(),
                            "Friend request sent to " + matchedUser.getName()));
        }
    }


    @Override
    public void skip(UserSession userSession, User matchedUser) {
        userSession.getIncomingMatches().remove(matchedUser);
        userSession.getOutgoingMatches().remove(matchedUser);
        // 可以加入 decline 记录，但你之前说不需要暂存回队列，就简单移除
    }
}

