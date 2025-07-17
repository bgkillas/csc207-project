package entities;

import java.util.ArrayList;
import java.util.List;
import spotify.Spotify;

/** Represents a session for a logged-in user. */
public class UserSession {
    private User user;
    private final List<User> incomingMatches;
    private final List<User> outgoingMatches;
    private final List<Match> matches;

    private Spotify spotify;

    /**
     * Constructs a UserSession for the given user.
     *
     * @param user the current user
     */
    public UserSession(User user) {
        this.user = user;
        this.incomingMatches = new ArrayList<>();
        this.outgoingMatches = new ArrayList<>();
        this.matches = new ArrayList<>();
    }

    public void initiateSpotify() {
        spotify = new Spotify();
    }

    public void updateSpotify() {
        spotify.pullTopArtistsAndGenres();
        this.user.setFavArtists(spotify.getTopArtists());
        this.user.setFavGenres(spotify.getGenres());

    }

    /**
     * Sets the current user for this session. This method is used when a user signs up or logs in
     * so the app can associate a User object with the current session. This is needed for account
     * creation, profile setup, and matching (need to know who the active user is) Changed the
     * 'user' field to not be final for this to work
     *
     * @param user the user to set as the current session user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Returns the current user for this session.
     *
     * @return the current user
     */
    public User getUser() {
        return user;
    }

    /**
     * Returns the list of users who have sent match requests to the current user.
     *
     * @return the list of incoming match users
     */
    public List<User> getIncomingMatches() {
        return incomingMatches;
    }

    /**
     * Returns the list of users to whom the current user has sent match requests.
     *
     * @return the list of outgoing match users
     */
    public List<User> getOutgoingMatches() {
        return outgoingMatches;
    }

    /**
     * Returns the list of matches for the current user.
     *
     * @return the list of matches
     */
    public List<Match> getMatches() {
        return matches;
    }

    /**
     * Adds a user to the list of incoming match requests.
     *
     * @param other the user who sent a match request
     */
    public void addIncomingMatch(User other) {
        incomingMatches.add(other);
    }

    /**
     * Adds a user to the list of outgoing match requests.
     *
     * @param other the user to whom a match request was sent
     */
    public void addOutgoingMatch(User other) {
        outgoingMatches.add(other);
    }

    /**
     * Adds a match to the list of matches for the current user.
     *
     * @param match the match to add
     */
    public void addMatch(Match match) {
        matches.add(match);
    }

    /**
     * Temporary no-argument constructor to allow creating an empty session Use for demo; for full
     * implementation, use constructor that takes a User
     */
    public UserSession() {
        this.user = null;
        this.incomingMatches = new ArrayList<>();
        this.outgoingMatches = new ArrayList<>();
        this.matches = new ArrayList<>();
    }
}
