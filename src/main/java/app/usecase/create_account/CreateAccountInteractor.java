package app.usecase.create_account;

import app.entities.User;
import app.entities.UserSession;
import app.frameworks_and_drivers.external.spotify.SpotifyInterface;
import app.usecase.login.LoginManager;
import java.util.ArrayList;

/**
 * Interactor for the Create Account use case. This class handles the creation of a new user account
 * based on Spotify authentication. It interacts with the Spotify interface to extract the user's ID
 * and username, registers the user in the login system if they don't already exist, and creates a
 * new User entity with default values for age, gender, location, and bio.
 */
public class CreateAccountInteractor implements CreateAccountInputBoundary {
    private final CreateAccountOutputBoundary presenter;
    private final UserSession session;
    private final LoginManager loginManager;

    /**
     * Constructs a new {CreateAccountInteractor}.
     *
     * @param presenter the output boundary to handle the success view
     * @param session the current user session
     * @param loginManager the login manager responsible for account registration
     */
    public CreateAccountInteractor(
            CreateAccountOutputBoundary presenter, UserSession session, LoginManager loginManager) {
        this.presenter = presenter;
        this.session = session;
        this.loginManager = loginManager;
    }

    /**
     * This method is called when a user tries to sign up using their Spotify account The Spotify
     * username is passed in by the controller.
     */
    @Override
    public void create(SpotifyInterface spotify) {


        session.initiateSpotify(spotify); // we don't want entity UserSession to know about spotify
//        // Instead below is the logic inside initiateSpotify()
//        spotify.initSpotify();
//        spotify.pullUserData();

        // Rather than pulling from session, pulling directly from our local variable makes sense more!
        String spotifyUserId = spotify.getUserId();

        // If the user hasn't already been registered in the login system,
        // register them using a dummy password ("spotify") to simulate login tracking
        if (!loginManager.hasLogin(spotifyUserId)) {
            loginManager.registerLogin(spotifyUserId, "spotify");
        }

        String spotifyUsername = spotify.getUserName();
        // Create a new User object using the Spotify username
        // This object represents the full user in the app (bio, preferences, etc.)
        User user =
                new User(
                        spotifyUsername,
                        18,
                        "Not specified",
                        "Not specified",
                        "Empty biography",
                        // favGenres, favArtists, and favSongs
                        new ArrayList<>(),
                        new ArrayList<>(),
                        new ArrayList<>());
        // updated UserSession to have a setter
        session.setUser(user);
        presenter.prepareSuccessView(user);
    }
}
