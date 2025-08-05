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
        System.out.println(spotify.getToken());
        spotify.refreshToken();
        System.out.println(spotify.getToken());
        spotify.pullUserData();
        System.out.println(spotify.getUserName());
        System.out.println(spotify.getUserId());
    }

    @Test
    public void testGenreSort() {
        final Spotify spotify = new Spotify();
        spotify.setTopGenres(List.of("a", "b", "a", "b", "a", "a", "c"));
        spotify.sortGenres();
        List<String> sorted = spotify.getTopGenres();
        assertEquals("a", sorted.get(0));
        assertEquals("b", sorted.get(1));
        assertEquals("c", sorted.get(2));
        assertEquals(3, sorted.size());
    }
}
