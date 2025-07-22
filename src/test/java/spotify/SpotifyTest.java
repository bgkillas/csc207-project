package spotify;

import org.junit.Ignore;
import org.junit.Test;

/** Tests if spotify api implementation is working. */
public class SpotifyTest {
    @Test
    @Ignore("Manual only")
    public void testSpotify() {
        final Spotify spotify = new Spotify();
        spotify.pullTopArtistsAndGenres();
        System.out.println(spotify.getTopArtists());
        System.out.println(spotify.getTopGenres());
        spotify.pullTopTracks();
        System.out.println(spotify.getTopTracks());
    }
}
