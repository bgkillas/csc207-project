package entities;

import java.util.List;
import java.util.UUID;

/**
 * Represents a match between the current user and another user,
 * along with their compatibility score and shared artists.
 */
public class Match {

    private final UUID matchId = UUID.randomUUID();
    private final User user;
    private final int compatibilityScore;
    private final List<String> sharedArtists;

    /**
     * Constructs a Match object.
     *
     * @param user           the user this match refers to
     * @param score          the compatibility score between users
     * @param sharedArtists  a list of artists both users like
     */
    public Match(User user, int score, List<String> sharedArtists) {
        this.user = user;
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
    public User getUser() {
        return user;
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
