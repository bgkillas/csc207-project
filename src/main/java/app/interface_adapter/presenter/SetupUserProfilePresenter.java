package app.interface_adapter.presenter;

import app.Main;
import app.entities.User;
import app.frameworks_and_drivers.view.MatchFilterSetupView;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import javax.swing.*;
import java.awt.*;

public class SetupUserProfilePresenter implements SetupUserProfileOutputBoundary {
    // new
    private final JFrame profileFrame;

    public SetupUserProfilePresenter(JFrame profileFrame) {
        this.profileFrame = profileFrame;
    }
    @Override
    public void prepareSuccessView(User user) {
        JOptionPane.showMessageDialog(profileFrame, "Profile updated for " + user.getName());

        // Launch the match filter setup view
        JPanel matchFilterPanel = MatchFilterSetupView.create(Main.getFilterController(), profileFrame);
        profileFrame.setContentPane(matchFilterPanel);
        profileFrame.setTitle("Set Your Match Filter");
        profileFrame.setPreferredSize(new Dimension(800, 600));
        profileFrame.pack();
        profileFrame.revalidate();
        profileFrame.repaint();
    }
}
