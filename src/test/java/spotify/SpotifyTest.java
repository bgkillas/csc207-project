package spotify;

import org.junit.Test;

/** Tests if spotify api implementation is working. */
public class SpotifyTest {
    @Test
    public void testSpotify() {
        final Spotify spotify = new Spotify();
        spotify.pullTopArtists();
    }
}
