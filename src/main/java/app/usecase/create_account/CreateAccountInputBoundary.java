package app.usecase.create_account;

/**
 * This interface defines the input boundary for account creation In a full implementation, a user
 * would log in through Spotify using OAuth The app would receive an access token from Spotify to
 * identify the user and access their data This simplifies this process for now by passing a Spotify
 * username directly as a String to simulate that the user has already authenticated with Spotify.
 */
public interface CreateAccountInputBoundary {
    void create();
}
