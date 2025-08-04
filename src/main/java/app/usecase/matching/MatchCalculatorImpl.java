package app.usecase.matching;

import app.entities.User;
import java.util.List;

/**
 * Class that calculates the compatibility score between users and determining if two users are
 * compatible matches.
 */
public class MatchCalculatorImpl implements MatchCalculator {

    /**
     * calculates the compatibility score between two users given their favourite artists and
     * genres.
     *
     * @param userOne the first user
     * @param userTwo the second user
     * @return an integer representing the compatibility score between two users
     */
    @Override
    public int calculateCompatibilityScore(User userOne, User userTwo) {
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
     * Determines if two users are compatible based on their compatibility score.
     *
     * @param userOne the first user
     * @param userTwo the second user
     * @return true if the users are compatible and false if not
     */
    @Override
    public boolean isCompatible(User userOne, User userTwo) {
        return calculateCompatibilityScore(userOne, userTwo) > 85;
    }
}
