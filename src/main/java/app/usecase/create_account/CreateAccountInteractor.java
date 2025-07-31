package app.usecase.create_account;

import app.entities.User;
import app.entities.UserSession;
import app.usecase.login.LoginManager;

import java.util.ArrayList;

public class CreateAccountInteractor implements CreateAccountInputBoundary {
    private final CreateAccountOutputBoundary presenter;
    private final UserSession session;
    private final LoginManager loginManager;

    public CreateAccountInteractor(
            CreateAccountOutputBoundary presenter, UserSession session, LoginManager loginManager) {
        this.presenter = presenter;
        this.session = session;
        this.loginManager = loginManager;
    }

    /**
     * This method is called when a user tries to sign up using their Spotify account The Spotify
     * username is passed in by the controller
     */
    @Override
    public void create(String spotifyUsername) {
        // If the user hasn't already been registered in the login system,
        // register them using a dummy password ("spotify") to simulate login tracking
        if (!loginManager.hasLogin(spotifyUsername)) {
            loginManager.registerLogin(spotifyUsername, "spotify");
        }
        // Create a new User object using the Spotify username
        // This object represents the full user in the app (bio, preferences, etc.)
        User user =
                new User(
                        spotifyUsername,
                        18, // default age (can update later in profile setup)
                        "Not specified", // default gender
                        "Not specified", // default location
                        "Empty biography", // default bio
                        new ArrayList<>(), // favGenres
                        new ArrayList<>(), // favArtists
                        new ArrayList<>() // favSongs
                        );
        // updated UserSession to have a setter
        session.setUser(user);
        presenter.prepareSuccessView(user);
    }
}
