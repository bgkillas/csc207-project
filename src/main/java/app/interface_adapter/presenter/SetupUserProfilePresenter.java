package app.interface_adapter.presenter;

import app.Main;
import app.entities.User;
import app.frameworks_and_drivers.view.MatchFilterSetupView;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import java.awt.*;
import javax.swing.*;

/**
 * Presenter class responsible for handling the output of the user profile setup use case.
 * Updates the UI to reflect success and transitions the user to the match filter setup view.
 */
public class SetupUserProfilePresenter implements SetupUserProfileOutputBoundary {
    private final JFrame profileFrame;

    /**
     * Constructs a SetupUserProfilePresenter with the given frame.
     *
     * @param profileFrame the shared JFrame used to display views.
     */
    public SetupUserProfilePresenter(JFrame profileFrame) {
        this.profileFrame = profileFrame;
    }

    /**
     * Displays a success message to the user and transitions the application to the
     * match filter setup view using the same frame.
     *
     * @param user the User whose profile was successfully set up.
     */
    @Override
    public void prepareSuccessView(User user) {
        JOptionPane.showMessageDialog(profileFrame, "Profile updated for " + user.getName());

        // Launch the match filter setup view
        JPanel matchFilterPanel =
                MatchFilterSetupView.create(Main.getFilterController(), profileFrame);
        profileFrame.setContentPane(matchFilterPanel);
        profileFrame.setTitle("Set Your Match Filter");
        profileFrame.setPreferredSize(new Dimension(800, 600));
        profileFrame.pack();
        profileFrame.revalidate();
        profileFrame.repaint();
    }
}
