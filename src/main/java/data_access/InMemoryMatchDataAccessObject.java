package data_access;

import entities.Match;
import entities.User;

import java.util.*;

public class InMemoryMatchDataAccessObject implements MatchDataAccessInterface {
    private final Map<User, List<User>> incoming = new HashMap<>();
    private final Map<User, List<User>> outgoing = new HashMap<>();
    private final Map<User, List<Match>> matches = new HashMap<>();

    @Override
    public List<User> getIncomingFriendRequest(User user) {
        return new ArrayList<>(incoming.getOrDefault(user, new ArrayList<>()));
    }

    @Override
    public List<User> getOutgoingFriendRequest(User user) {
        return new ArrayList<>(outgoing.getOrDefault(user, new ArrayList<>()));
    }

    @Override
    public List<Match> getMatches(User user) {
        return new ArrayList<>(matches.getOrDefault(user, new ArrayList<>()));
    }

    @Override
    public void addIncomingFriendRequest(User toUser, User fromUser) {
        // Make sure toUser has a list of incoming Friend Request. If not, create one then add fromUser to that list.
        incoming.computeIfAbsent(toUser, k -> new ArrayList<>()).add(fromUser);
    }

    @Override
    public void addOutgoingFriendRequest(User fromUser, User toUser) {
        // Make sure fromUser has a list of outgoing Friend Request. If not, create one then add toUser to that list.
        outgoing.computeIfAbsent(fromUser, k -> new ArrayList<>()).add(toUser);
    }

    @Override
    public void addMatch(User user, Match match) {
        matches.computeIfAbsent(user, k -> new ArrayList<>()).add(match);
    }
}
