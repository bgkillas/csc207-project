package app.frameworks_and_drivers.external.spotify;

import java.util.List;

/** for testing purposes does not actually connect to spotify api. */
public class SpotifyOffline implements SpotifyInterface {
    @Override
    public void initSpotify() {}

    @Override
    public void pullTopArtistsAndGenres() {}

    @Override
    public void pullTopTracks() {}

    @Override
    public void pullUserData() {}

    @Override
    public void refreshToken() {}

    @Override
    public String getUserId() {
        return "";
    }

    @Override
    public String getUserName() {
        return "";
    }

    @Override
    public List<String> getTopArtists() {
        return List.of();
    }

    @Override
    public List<String> getTopTracks() {
        return List.of();
    }

    @Override
    public List<String> getTopGenres() {
        return List.of();
    }
}
