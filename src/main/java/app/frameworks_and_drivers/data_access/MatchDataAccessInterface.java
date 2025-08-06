package app.frameworks_and_drivers.data_access;

import app.entities.Match;
import app.entities.User;
import java.util.List;

/**
 * Interface defining data access operations related to friend requests and matches between users.
 */
public interface MatchDataAccessInterface {
    /**
     * Retrieves the list of users who have sent friend requests to the specified user.
     *
     * @param user the user receiving friend requests
     * @return a list of users who sent friend requests
     */
    List<User> getIncomingFriendRequest(User user);

    /**
     * Retrieves the list of users to whom the specified user has sent friend requests.
     *
     * @param user the user who sent friend requests
     * @return a list of users who received friend requests
     */
    List<User> getOutgoingFriendRequest(User user);

    /**
     * Retrieves the list of matches associated with a given user.
     *
     * @param user the user whose matches are to be retrieved
     * @return a list of matches for the user
     */
    List<Match> getMatches(User user);

    /**
     * Adds an incoming friend request entry to the specified recipient user.
     *
     * @param toUser the user receiving the friend request
     * @param fromUser the user sending the friend request
     */
    void addIncomingFriendRequest(User toUser, User fromUser);

    /**
     * Adds an outgoing friend request entry for the specified sender user.
     *
     * @param fromUser the user sending the friend request
     * @param toUser the user receiving the friend request
     */
    void addOutgoingFriendRequest(User fromUser, User toUser);

    /**
     * Adds a match entry to the user's list of matches.
     *
     * @param user the user for whom the match is being added
     * @param match the match object to add
     */
    void addMatch(User user, Match match);
}
