package app.frameworks_and_drivers.data_access;

import app.entities.Match;
import app.entities.User;
import java.util.List;

public interface MatchDataAccessInterface {
    List<User> getIncomingFriendRequest(User user);

    List<User> getOutgoingFriendRequest(User user);

    List<Match> getMatches(User user);

    void addIncomingFriendRequest(User toUser, User fromUser);

    void addOutgoingFriendRequest(User fromUser, User toUser);

    void addMatch(User user, Match match);
}
