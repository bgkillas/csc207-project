package app;

// import app.frameworks_and_drivers.view.LoginView;
import app.entities.*;
import app.frameworks_and_drivers.data_access.*;
import app.frameworks_and_drivers.view.DebugMenuView;
import app.frameworks_and_drivers.view.LoginView;
import app.interface_adapter.controller.CreateAccountController;
import app.interface_adapter.controller.SetupMatchFilterController;
import app.interface_adapter.controller.SetupUserProfileController;
import app.interface_adapter.presenter.CreateAccountPresenter;
import app.interface_adapter.presenter.SetupMatchFilterPresenter;
import app.interface_adapter.presenter.SetupUserProfilePresenter;
import app.usecase.create_account.CreateAccountInputBoundary;
import app.usecase.create_account.CreateAccountInteractor;
import app.usecase.create_account.CreateAccountOutputBoundary;
import app.usecase.login.LoginManager;
import app.usecase.login.LoginManagerMemory;
import app.usecase.matchfilter.SetupMatchFilterInputBoundary;
import app.usecase.matchfilter.SetupMatchFilterInteractor;
import app.usecase.matchfilter.SetupMatchFilterOutputBoundary;
import app.usecase.user_profile_setup.SetupUserProfileInputBoundary;
import app.usecase.user_profile_setup.SetupUserProfileInteractor;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/** Main executable class. */
public class Main {
    private static SetupMatchFilterController filterController;
    private static SetupUserProfileController setupController;

    /** Main executable point. */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        final JFrame application = new JFrame("app");
        final JPanel views = new JPanel();
        final LoginManager login_manager = new LoginManagerMemory();

        // Shared user session across the app
        UserSession session = new UserSession();

        // initalize postDataAccessObject
        PostDataAccessInterface postDataAccessObject = new InMemoryPostDataAccessObject();

        UserDataAccessInterface userDataAccessObject = new InMemoryUserDataAccessObject();
        MatchDataAccessInterface matchDataAccessObject = new InMemoryMatchDataAccessObject();
        // ==== Dummy User Setup (for demo) ====

        User alice =
                new User(
                        "Alice",
                        22,
                        "female",
                        "Toronto",
                        "Loves concerts and pop music",
                        List.of("Pop", "Rock"),
                        List.of("Taylor Swift", "Adele", "Coldplay"),
                        List.of("Shake It Off", "Hello", "Yellow"));
        User bob =
                new User(
                        "Bob",
                        24,
                        "male",
                        "Toronto",
                        "Hip-hop and basketball fan",
                        List.of("Hip Hop"),
                        List.of("Drake", "Kanye West"),
                        List.of("Hotline Bling", "Stronger"));
        User charlie =
                new User(
                        "Charlie",
                        23,
                        "non-binary",
                        "Montreal",
                        "Chill lo-fi beats all day",
                        List.of("Lo-fi", "Jazz"),
                        List.of("Joji", "Norah Jones"),
                        List.of("Sanctuary", "Don’t Know Why"));
        User javaa =
                new User(
                        "Java",
                        22,
                        "female",
                        "Toronto",
                        "Indie lover, always looking for concert buddies",
                        List.of("Indie", "Folk"),
                        List.of("Phoebe Bridgers", "Bon Iver"),
                        List.of("Motion Sickness", "Skinny Love"));

        User pythonn =
                new User(
                        "Python",
                        24,
                        "male",
                        "Toronto",
                        "Hip-hop fan and amateur DJ",
                        List.of("Hip-hop", "Rap"),
                        List.of("Kendrick Lamar", "Drake"),
                        List.of("HUMBLE.", "Hotline Bling"));

        User cplus =
                new User(
                        "C++",
                        27,
                        "non-binary",
                        "Montreal",
                        "Electronic vibes only",
                        List.of("Electronic", "House"),
                        List.of("Deadmau5", "Calvin Harris"),
                        List.of("Ghosts 'n' Stuff", "Summer"));
        User diana =
                new User(
                        "Diana",
                        21,
                        "female",
                        "Vancouver",
                        "EDM is life!",
                        List.of("EDM", "Pop"),
                        List.of("Zedd", "Avicii"),
                        List.of("Clarity", "Wake Me Up"));
        User eric =
                new User(
                        "Eric",
                        25,
                        "male",
                        "Calgary",
                        "Guitarist and metalhead",
                        List.of("Metal", "Rock"),
                        List.of("Metallica", "Nirvana"),
                        List.of("Enter Sandman", "Smells Like Teen Spirit"));


        // temporary code for dummy post to show up in demo after user befriends Alice
        Post post =
                new Post(
                        "Anybody going to the coldplay concert?",
                        "Hi, I'm looking for some friends to go to concerts together! Comment here"
                                + " and lmk :)",
                        null,
                        LocalDateTime.now(),
                        alice,
                        null);
        Comment comment1 = new Comment("Hey that sounds fun!!", bob, LocalDateTime.now());
        Comment comment2 = new Comment("OMG I'm so down", charlie, LocalDateTime.now());

        post.setComments(List.of(comment1, comment2));

        postDataAccessObject.savePost(post);
        session.setPosts(List.of(post)); // for now start with having this post
        session.getIncomingMatches().add(alice); // this puts alice in the friendRequest view.


        session.getIncomingMatches().add(cplus);
        session.getIncomingMatches().add(javaa);
        session.getIncomingMatches().add(pythonn);

        // Add users to DataAccessObject and session
        for (User user : List.of(alice, bob, charlie)) {
            userDataAccessObject.addUser(user);
            session.addUser(user);
        }

        // Match Filter setup
        SetupMatchFilterOutputBoundary filterPresenter =
                new SetupMatchFilterPresenter(application, session, postDataAccessObject, true);

        SetupMatchFilterInputBoundary filterInteractor =
                new SetupMatchFilterInteractor(filterPresenter, session);
        filterController = new SetupMatchFilterController(filterInteractor);

        // User Profile setup
        SetupUserProfileOutputBoundary setupPresenter = new SetupUserProfilePresenter(application);
        SetupUserProfileInputBoundary setupInteractor =
                new SetupUserProfileInteractor(setupPresenter, session);
        setupController = new SetupUserProfileController(setupInteractor);

        // Create Account setup
        CreateAccountOutputBoundary createAccountPresenter =
                new CreateAccountPresenter(application, setupController);
        CreateAccountInputBoundary createAccountInteractor =
                new CreateAccountInteractor(createAccountPresenter, session, login_manager);
        CreateAccountController createAccountController =
                new CreateAccountController(createAccountInteractor);

        if (args.length > 0 && args[0].equals("--debug")) {
            // Connecting to DebugMenuView
            final JPanel debugView = DebugMenuView.create(session);
            views.add(debugView);
        } else {
            // Initial Login View
            final JPanel login = LoginView.create(login_manager, createAccountController);
            views.add(login);
        }

        application.add(views);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setVisible(true);
    }

    public static SetupMatchFilterController getFilterController() {
        return filterController;
    }

    public static SetupUserProfileController getSetupController() {
        return setupController;
    }
}
