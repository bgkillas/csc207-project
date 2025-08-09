package app.interface_adapter.presenter;

import app.entities.User;
import app.frameworks_and_drivers.view.MatchFilterSetupView;
import app.interface_adapter.controller.SetupMatchFilterController;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import java.awt.*;
import javax.swing.*;

/**
 * Presenter class responsible for handling the output of the user profile setup use case. Updates
 * the UI to reflect success and transitions the user to the match filter setup view.
 */
public class SetupUserProfilePresenter implements SetupUserProfileOutputBoundary {
    private final JFrame profileFrame;
    private final SetupMatchFilterController filterController;

    /**
     * Constructs a SetupUserProfilePresenter with the given frame.
     *
     * @param profileFrame the shared JFrame used to display views.
     */
    public SetupUserProfilePresenter(JFrame profileFrame,
                                     SetupMatchFilterController filterController) {
        this.profileFrame = profileFrame;
        this.filterController = filterController;
    }

    /**
     * Displays a success message to the user and transitions the application to the match filter
     * setup view using the same frame.
     *
     * @param user the User whose profile was successfully set up.
     */
    @Override
    public void prepareSuccessView(User user) {
        JOptionPane.showMessageDialog(profileFrame, "Profile updated for " + user.getName());

        // Launch the match filter setup view
        JPanel matchFilterPanel =
                MatchFilterSetupView.create(filterController, profileFrame);
        profileFrame.setContentPane(matchFilterPanel);
        profileFrame.setTitle("Set Your Match Filter");
        profileFrame.setPreferredSize(new Dimension(800, 600));
        profileFrame.pack();
        profileFrame.revalidate();
        profileFrame.repaint();
    }
}
