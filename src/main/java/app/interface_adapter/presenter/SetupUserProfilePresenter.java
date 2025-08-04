package app.interface_adapter.presenter;

import app.Main;
import app.entities.User;
import app.frameworks_and_drivers.view.MatchFilterSetupView;
import app.usecase.user_profile_setup.SetupUserProfileOutputBoundary;
import javax.swing.*;

public class SetupUserProfilePresenter implements SetupUserProfileOutputBoundary {
    @Override
    public void prepareSuccessView(User user) {
        JOptionPane.showMessageDialog(null, "Profile updated for " + user.getName());

        // Launch the match filter setup view
        JFrame frame = new JFrame("Set Your Match Filter");
        JPanel matchFilterPanel = MatchFilterSetupView.create(Main.getFilterController(), frame);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(matchFilterPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
