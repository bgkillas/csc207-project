package entities;

import java.util.List;

public class MatchCalculator {

    public static int calculateCompatibilityScore(User userOne, User userTwo) {
        int score = 0;

        List<String> userOneArtists = userOne.getFavArtists();
        List<String> userTwoArtists = userTwo.getFavArtists();

        for (int i = 0; i < 10; i++) {
            String artist = userOneArtists.get(i);
            if (userTwoArtists.contains(artist)) {
                int j = userTwoArtists.indexOf(artist);
                score += (10 - i) + (10 - j);
            }
        }

        List<String> userOneGenres = userOne.getFavGenres();
        List<String> userTwoGenres = userTwo.getFavGenres();

        for (int i = 0; i <= 10; i += 5) {
            String genre = userOneGenres.get(i);
            if (userTwoGenres.contains(genre)) {
                int j = userTwoGenres.indexOf(genre);
                score += (15 - i) + (15 - j);
            }
        }

        return score;
    }

    public static boolean isCompatible(User userOne, User userTwo) {
        return calculateCompatibilityScore(userOne, userTwo) > 85;
    }
}
