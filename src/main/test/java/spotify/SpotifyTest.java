package spotify;

import app.frameworks_and_drivers.external.spotify.Spotify;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.assertEquals;

/** Tests if spotify api implementation is working. */
public class SpotifyTest {
    @Test
    @Ignore("Manual only")
    public void testSpotify() {
        final Spotify spotify = new Spotify();
        spotify.initSpotify();
        spotify.pullTopArtistsAndGenres();
        System.out.println(spotify.getTopArtists());
        System.out.println(spotify.getTopGenres());
        spotify.pullTopTracks();
        System.out.println(spotify.getTopTracks());
        System.out.println(spotify.token);
        spotify.refreshToken();
        System.out.println(spotify.token);
    }

    @Test
    public void testGenreSort() {
        final Spotify spotify = new Spotify();
        spotify.topGenres = List.of("a", "b", "a", "b", "a", "a", "c");
        spotify.sortGenres();
        assertEquals("a", spotify.topGenres.get(0));
        assertEquals("b", spotify.topGenres.get(1));
        assertEquals("c", spotify.topGenres.get(2));
        assertEquals(3, spotify.topGenres.size());
    }
}
