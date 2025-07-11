package spotify;

import java.util.List;

/** Interface for spotify interactions. */
public interface SpotifyInterface {
    /** Pulls top artists in the user profile to the class. */
    void pullTopArtists();

    /** gets top artists in the user profile to the class. */
    List<String> getTopArtists();
}
