package app.interface_adapter.presenter;

import app.entities.MatchFilter;
import app.entities.User;
import app.entities.UserSession;
import app.usecase.matchfilter.SetupMatchFilterOutputBoundary;
import app.usecase.matching.MatchServiceImpl;
import app.frameworks_and_drivers.view.MatchingRoomView;

import javax.swing.*;
import java.awt.*;
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
                frame,
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

        frame.setTitle("Matching Room");
        JPanel matchingRoomPanel = new MatchingRoomView(frame, currentUser, matches, session);
        frame.setContentPane(matchingRoomPanel);
        frame.setPreferredSize(new Dimension(800, 600)); // match size
        frame.pack();
        frame.revalidate();
        frame.repaint();
    }
}
