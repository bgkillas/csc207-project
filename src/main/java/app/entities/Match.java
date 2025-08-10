package app.entities;

import java.util.List;
import java.util.UUID;

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
    // ...existing code...

    /**
     * Constructs a Match with provided score and shared artists list.
     *
     * @param matchUser the user this match refers to
     * @param score the compatibility score between users
     * @param sharedArtists a list of favorite artists shared by both users
     */
    public Match(User matchUser, int score, List<String> sharedArtists) {
        this.matchUser = matchUser;
        this.compatibilityScore = score;
        this.sharedArtists = sharedArtists;
    }

    /**
     * Constructs a Match by calculating the score and shared artists between the current user and
     * the matched user.
     *
     * @param currentUser the current user
     * @param other the user to be matched with
     */
    public Match(User currentUser, User other) {
        this.matchUser = other;
        this.compatibilityScore = calculateCompatibilityScore(currentUser, other);
        // Find shared artists
        List<String> shared = new java.util.ArrayList<>();
        for (String artist : currentUser.getFavArtists()) {
            if (other.getFavArtists().contains(artist)) {
                shared.add(artist);
            }
        }
        this.sharedArtists = shared;
    }

    // Compatibility logic inlined from previous calculator implementation
    private int calculateCompatibilityScore(User userOne, User userTwo) {
        int score = 0;

        List<String> userOneArtists = userOne.getFavArtists();
        List<String> userTwoArtists = userTwo.getFavArtists();

        int artistsListLength = userOneArtists.size();

        for (int i = 0; i < artistsListLength; i++) {
            String artist = userOneArtists.get(i);
            if (userTwoArtists.contains(artist)) {
                int j = userTwoArtists.indexOf(artist);
                score += (artistsListLength - i) + (artistsListLength - j);
            }
        }

        List<String> userOneGenres = userOne.getFavGenres();
        List<String> userTwoGenres = userTwo.getFavGenres();

        int genresListLength = userOneGenres.size();

        for (int i = 0; i < genresListLength; i++) {
            String genre = userOneGenres.get(i);
            if (userTwoGenres.contains(genre)) {
                int j = userTwoGenres.indexOf(genre);
                score += ((genresListLength - i) + (genresListLength - j)) * 5;
            }
        }

        return score;
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
