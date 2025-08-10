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


        // We will call Spotify API to pull user data!
        spotify.initSpotify();
        spotify.pullUserData();

        // the spotify object is filled with required info including userID, username, and some  fav lists.
        String spotifyUserId = spotify.getUserId();

        // If the user hasn't already been registered in the login system,
        // register them using a dummy password ("spotify") to simulate login tracking
        if (!loginManager.hasLogin(spotifyUserId)) {
            loginManager.registerLogin(spotifyUserId, "spotify");
        }

        // Create a new User object using the Spotify username and default information.
        // This object represents the full user in the app (bio, preferences, etc.)
        String spotifyUsername = spotify.getUserName();
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

        // Now we set the userSession owned by this user object.
        session.setUser(user);
        // the user object's default data will be overwritten with information from spotify.
        updateUserWithSpotifyData(user, spotify);

        // Now, for the purpose of the demo, we will add pre-existing friends and friend requests.
        if (user.getFriendList().isEmpty()) {
            session.addDefaultFriends();
        }
        session.addDummyIncomingRequests();

        // we also want the user of the userSession to be compatible with all the other users.
        // Below updates UserSession to have a setter
        session.makeAllUsersCompatible(user);
        presenter.prepareSuccessView(user);
    }

    /** Updates the user's top tracks, artists, and genres using Spotify data. */
    private void updateUserWithSpotifyData(User user, SpotifyInterface spotify) {
        if (user != null && spotify != null) {
            spotify.pullTopArtistsAndGenres();
            user.setFavArtists(spotify.getTopArtists());
            user.setFavGenres(spotify.getTopGenres());

            spotify.pullTopTracks();
            user.setFavSongs(spotify.getTopTracks());
        }
    }

}
