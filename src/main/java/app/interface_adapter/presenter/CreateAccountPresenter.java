package app.interface_adapter.presenter;

import app.entities.User;
import app.frameworks_and_drivers.view.ProfileSetupView;
import app.interface_adapter.controller.SetupUserProfileController;
import app.usecase.create_account.CreateAccountOutputBoundary;
import javax.swing.*;

/**
 * Presenter for the Create Account use case.
 * This presenter is responsible for transitioning the user interface
 * to the profile setup screen after successful account creation.
 */
public class CreateAccountPresenter implements CreateAccountOutputBoundary {
    private final JFrame frame;
    private final SetupUserProfileController setupController;

    /**
     * Constructs a new {CreateAccountPresenter}.
     *
     * @param frame           the main application window
     * @param setupController the controller used to initiate profile setup
     */
    public CreateAccountPresenter(JFrame frame, SetupUserProfileController setupController) {
        this.frame = frame;
        this.setupController = setupController;
    }

    /**
     * Called when a user account is successfully created.
     * Updates the application's main frame to display the profile setup view.
     *
     * @param user the newly created User object
     */
    @Override
    public void prepareSuccessView(User user) {
        // Replace the content pane with the profile setup view
        // JOptionPane.showMessageDialog(frame, "Account created for: " + user.getName());
        frame.setContentPane(ProfileSetupView.create(setupController, user));
        frame.pack();
    }
}
