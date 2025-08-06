package app.frameworks_and_drivers.data_access;

import app.entities.Match;
import app.entities.User;
import java.util.*;

/**
 * In-memory implementation of the MatchDataAccessInterface. Stores incoming and outgoing friend
 * requests and confirmed matches using HashMaps. This class is primarily used for testing/demo and
 * does not persist data.
 */
public class InMemoryMatchDataAccessObject implements MatchDataAccessInterface {
    private final Map<User, List<User>> incoming = new HashMap<>();
    private final Map<User, List<User>> outgoing = new HashMap<>();
    private final Map<User, List<Match>> matches = new HashMap<>();

    /**
     * Retrieves the list of incoming friend requests for the given user.
     *
     * @param user the user to query
     * @return a list of users who have sent a friend request
     */
    @Override
    public List<User> getIncomingFriendRequest(User user) {
        return new ArrayList<>(incoming.getOrDefault(user, new ArrayList<>()));
    }

    /**
     * Retrieves the list of outgoing friend requests from the given user.
     *
     * @param user the user to query
     * @return a list of users to whom the friend request was sent
     */
    @Override
    public List<User> getOutgoingFriendRequest(User user) {
        return new ArrayList<>(outgoing.getOrDefault(user, new ArrayList<>()));
    }

    /**
     * Retrieves the list of matches for the given user.
     *
     * @param user the user to query
     * @return a list of {@link Match} objects
     */
    @Override
    public List<Match> getMatches(User user) {
        return new ArrayList<>(matches.getOrDefault(user, new ArrayList<>()));
    }

    /**
     * Adds an incoming friend request for the given user.
     *
     * @param toUser the recipient of the friend request
     * @param fromUser the sender of the friend request
     */
    @Override
    public void addIncomingFriendRequest(User toUser, User fromUser) {
        // Make sure toUser has a list of incoming Friend Request. If not, create one then add
        // fromUser to that list.
        incoming.computeIfAbsent(toUser, k -> new ArrayList<>()).add(fromUser);
    }

    /**
     * Adds an outgoing friend request from the given user.
     *
     * @param fromUser the sender of the friend request
     * @param toUser the recipient of the friend request
     */
    @Override
    public void addOutgoingFriendRequest(User fromUser, User toUser) {
        // Make sure fromUser has a list of outgoing Friend Request. If not, create one then add
        // toUser to that list.
        outgoing.computeIfAbsent(fromUser, k -> new ArrayList<>()).add(toUser);
    }

    /**
     * Adds a match to the list of matches for the given user.
     *
     * @param user the user to whom the match is added
     * @param match the match object to be added
     */
    @Override
    public void addMatch(User user, Match match) {
        matches.computeIfAbsent(user, k -> new ArrayList<>()).add(match);
    }
}
