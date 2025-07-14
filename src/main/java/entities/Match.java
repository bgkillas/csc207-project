package entities;

import java.util.List;
import java.util.UUID;

/**
 * Represents a match between the current user and another user,
 * along with their compatibility score and shared artists.
 */
public class Match {

    private final UUID matchId = UUID.randomUUID();
    private final User matchUser;
    private final int compatibilityScore;
    private final List<String> sharedArtists;

    /**
     * Constructs a Match object.
     *
     * @param matchUser           the user this match refers to
     * @param score          the compatibility score between users
     * @param sharedArtists  a list of artists both users like
     */
    public Match(User matchUser, int score, List<String> sharedArtists) {
        this.matchUser = matchUser;
        this.compatibilityScore = score;
        this.sharedArtists = sharedArtists;
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
