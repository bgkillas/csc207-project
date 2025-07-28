package entities;

import java.util.List;
import java.util.UUID;

import usecase.team_story.MatchCalculator;
import app.team_story.MatchCalculatorImpl;

/**
 * Represents a match between the current user and another user, along with their compatibility
 * score and shared artists.
 */
public class Match {

    private final UUID matchId = UUID.randomUUID();
    private final User matchUser;
    private final int compatibilityScore;
    private final List<String> sharedArtists;

    /**
     * Constructs a Match object.
     *
     * @param matchUser the user this match refers to
     * @param score the compatibility score between users
     * @param sharedArtists a list of artists both users like
     */

    MatchCalculator matchCalculator = new MatchCalculatorImpl();

    public Match(User matchUser, int score, List<String> sharedArtists) {
        this.matchUser = matchUser;
        this.compatibilityScore = score;
        this.sharedArtists = sharedArtists;
    }

    public Match(User currentUser, User other) {
        this.matchUser = other;
        this.compatibilityScore = matchCalculator.calculateCompatibilityScore(currentUser, other);
        // Find shared artists
        List<String> shared = new java.util.ArrayList<>();
        for (String artist : currentUser.getFavArtists()) {
            if (other.getFavArtists().contains(artist)) {
                shared.add(artist);
            }
        }
        this.sharedArtists = shared;
    }

    /**
     * Returns the compatibility score of the match.
     *
     * @return the compatibility score
     */
    public int getCompatibilityScore() {
        return compatibilityScore;
    }

    /**
     * Returns the user matched with.
     *
     * @return the matched user
     */
    public User getMatchUser() {
        return matchUser;
    }

    /**
     * Returns the list of shared artists.
     *
     * @return the list of shared favorite artists
     */
    public List<String> getSharedArtists() {
        return sharedArtists;
    }
}
