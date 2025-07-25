package interface_adapter.presentor;

import entities.User;
import usecase.team_story.SetupUserProfileOutputBoundary;
import view.MatchFilterSetupView;

import javax.swing.*;

public class SetupUserProfilePresenter implements SetupUserProfileOutputBoundary {
    @Override
    public void prepareSuccessView(User user) {
        JOptionPane.showMessageDialog(null, "Profile updated for " + user.getName());

        // Launch the match filter setup view
        JPanel matchFilterPanel = MatchFilterSetupView.create(app.Main.getFilterController());

        JFrame frame = new JFrame("Set Your Match Filter");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(matchFilterPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
