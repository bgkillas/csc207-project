package entities;

import java.util.ArrayList;
import java.util.List;

public class UserSession {
    private final User user;
    private final List<User> incomingMatches;
    private final List<User> outgoingMatches;
    private final List<Match> matches;

    public UserSession(User user) {
        this.user = user;
        this.incomingMatches = new ArrayList<>();
        this.outgoingMatches = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public List<User> getIncomingMatches() {
        return incomingMatches;
    }

    public List<User> getOutgoingMatches() {
        return outgoingMatches;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void addIncomingMatch(User other) {
        incomingMatches.add(other);
    }

    public void addOutgoingMatch(User other) {
        outgoingMatches.add(other);
    }

    public void addMatch(Match match) {
        matches.add(match);
    }
}
