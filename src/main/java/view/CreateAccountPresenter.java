package view;

import entities.User;
import usecase.teamStory.CreateAccountOutputBoundary;

import javax.swing.*;
import java.awt.*;
import account.login.SetupUserProfileController;

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
        JOptionPane.showMessageDialog(frame, "Account created for: " + user.getName());
        frame.setContentPane(ProfileSetupView.create(setupController));
        frame.pack();
    }
}
