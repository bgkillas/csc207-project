package interface_adapter.presentor;

import entities.MatchFilter;
import entities.User;
import entities.UserSession;
import usecase.teamStory.SetupMatchFilterOutputBoundary;
import app.teamStory.MatchServiceImpl;
import view.MatchingRoomView;

import javax.swing.*;
import java.util.List;

public class SetupMatchFilterPresenter implements SetupMatchFilterOutputBoundary {

    private final JFrame frame;
    private final UserSession session;

    public SetupMatchFilterPresenter(JFrame frame, UserSession session) {
        this.frame = frame;
        this.session = session;
    }

    @Override
    public void prepareSuccessView(MatchFilter filter) {
        JOptionPane.showMessageDialog(
                null,
                "Match filter set!\nAge: "
                        + filter.getMinAge()
                        + "-"
                        + filter.getMaxAge()
                        + "\nGender: "
                        + filter.getPreferredGender()
                        + "\nLocation: "
                        + filter.getPreferredLocation());

        User currentUser = session.getUser();
        List<User> allUsers = session.getAllUsers();

        List<User> matches = new MatchServiceImpl().findMatches(currentUser, allUsers);

        JPanel matchingRoomPanel = new MatchingRoomView(frame, currentUser, matches, session);
        frame.setContentPane(matchingRoomPanel);
        frame.revalidate();
        frame.repaint();
    }
}
