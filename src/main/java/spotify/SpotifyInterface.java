package spotify;

import java.util.List;

/** Interface for spotify interactions. */
public interface SpotifyInterface {
    /** Pulls top artists and genres in the user profile to the class. */
    void pullTopArtistsAndGenres();

    /** gets top artists in the user profile from the class. limited to at most 10 unique artists */
    List<String> getTopArtists();

    /**
     * gets top genres in the user profile from the class. arbitrary amount of potentially
     * duplicated unsorted genres
     */
    List<String> getGenres();
}
