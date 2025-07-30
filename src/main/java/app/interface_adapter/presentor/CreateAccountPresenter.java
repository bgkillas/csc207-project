package app.interface_adapter.presentor;

import app.entities.User;
import app.usecase.create_account.CreateAccountOutputBoundary;

import javax.swing.*;

import app.interface_adapter.controller.SetupUserProfileController;
import app.frameworks_and_drivers.view.ProfileSetupView;

public class CreateAccountPresenter implements CreateAccountOutputBoundary {
    private final JFrame frame;
    private final SetupUserProfileController setupController;

    public CreateAccountPresenter(JFrame frame, SetupUserProfileController setupController) {
        this.frame = frame;
        this.setupController = setupController;
    }

    @Override
    public void prepareSuccessView(User user) {
        // Replace the content pane with the profile setup view
        // JOptionPane.showMessageDialog(frame, "Account created for: " + user.getName());
        frame.setContentPane(ProfileSetupView.create(setupController, user));
        frame.pack();
    }
}
