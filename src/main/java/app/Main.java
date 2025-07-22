package app;

import account.login.SetupMatchFilterController;
import usecase.teamStory.SetupMatchFilterInputBoundary;
import usecase.teamStory.SetupMatchFilterInteractor;
import usecase.teamStory.SetupMatchFilterOutputBoundary;
import interface_adapter.presentor.SetupMatchFilterPresenter;

import account.login.SetupUserProfileController;
import usecase.teamStory.SetupUserProfileInputBoundary;
import usecase.teamStory.SetupUserProfileInteractor;
import usecase.teamStory.SetupUserProfileOutputBoundary;
import interface_adapter.presentor.SetupUserProfilePresenter;

import account.login.CreateAccountController;
import account.login.LoginManager;
import account.login.LoginManagerMemory;
import entities.UserSession;

import usecase.teamStory.CreateAccountInputBoundary;
import usecase.teamStory.CreateAccountInteractor;
import usecase.teamStory.CreateAccountOutputBoundary;
import interface_adapter.presentor.CreateAccountPresenter;
import view.DebugMenuView;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.security.NoSuchAlgorithmException;

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
        session.initiateSpotify();

        // Match Filter setup
        SetupMatchFilterOutputBoundary filterPresenter =
                new SetupMatchFilterPresenter(application, session);

        SetupMatchFilterInputBoundary filterInteractor =
                new SetupMatchFilterInteractor(filterPresenter, session);
        filterController = new SetupMatchFilterController(filterInteractor);

        // User Profile setup
        SetupUserProfileOutputBoundary setupPresenter = new SetupUserProfilePresenter();
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
        /*final JPanel login = LoginView.create(login_manager, createAccountController);
        views.add(login);*/

        // Connecting to DebugMenuView
        final JPanel debugView = DebugMenuView.create(session);
        views.add(debugView);

        application.add(views);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setVisible(true);
    }

    public static SetupMatchFilterController getFilterController() {
        return filterController;
    }
}
