package app.frameworks_and_drivers.external.spotify;

import java.util.List;

/** Interface for spotify interactions. */
public interface SpotifyInterface {
    /** sets up spotify tokens and such and calls the browser to get auth token. */
    void initSpotify();

    /** Pulls top artists and genres in the user profile to the class. */
    void pullTopArtistsAndGenres();

    /** Pulls top tracks in the user profile to the class. */
    void pullTopTracks();

    /** Pulls the user id and name from the user profile to the class. */
    void pullUserData();

    /** refreshes the token as it expires after 1 hour if not manually refreshed. */
    void refreshToken();

    /** gets the previously pulled userid. */
    String getUserId();

    /** gets the previously pulled user name. */
    String getUserName();

    /** gets top artists in the user profile from the class. limited to at most 10 unique artists */
    List<String> getTopArtists();

    /** gets top tracks in the user profile from the class. limited to at most 5 unique artists */
    List<String> getTopTracks();

    /**
     * gets top genres in the user profile from the class. arbitrary amount of potentially
     * duplicated unsorted genres
     */
    List<String> getTopGenres();
}
