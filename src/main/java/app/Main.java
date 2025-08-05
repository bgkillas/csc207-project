package app;

// import app.frameworks_and_drivers.view.LoginView;
import app.entities.UserSession;
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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/** Main executable class. */
public class Main {
    private static SetupMatchFilterController filterController;

    /** Main executable point. */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        final JFrame application = new JFrame("app");
        final JPanel views = new JPanel();
        final LoginManager login_manager = new LoginManagerMemory();

        // Shared user session across the app
        UserSession session = new UserSession();

        // Match Filter setup
        SetupMatchFilterOutputBoundary filterPresenter =
                new SetupMatchFilterPresenter(application, session);

        SetupMatchFilterInputBoundary filterInteractor =
                new SetupMatchFilterInteractor(filterPresenter, session);
        filterController = new SetupMatchFilterController(filterInteractor);

        // User Profile setup
        SetupUserProfileOutputBoundary setupPresenter = new SetupUserProfilePresenter(application);
        SetupUserProfileInputBoundary setupInteractor =
                new SetupUserProfileInteractor(setupPresenter, session);
        SetupUserProfileController setupController =
                new SetupUserProfileController(setupInteractor);

        // Create Account setup
        CreateAccountOutputBoundary createAccountPresenter =
                new CreateAccountPresenter(application, setupController);
        CreateAccountInputBoundary createAccountInteractor =
                new CreateAccountInteractor(createAccountPresenter, session, login_manager);
        CreateAccountController createAccountController =
                new CreateAccountController(createAccountInteractor);

        // Initial Login View
        final JPanel login = LoginView.create(login_manager, createAccountController);
        views.add(login);

        // Connecting to DebugMenuView
        /*        final JPanel debugView = DebugMenuView.create(session);
        views.add(debugView);*/

        application.add(views);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setVisible(true);
    }

    public static SetupMatchFilterController getFilterController() {
        return filterController;
    }
}
