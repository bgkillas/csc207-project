package view;

import entities.Match;
import entities.User;
import entities.UserSession;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ConnectRequestView extends JPanel {
    private int currentIndex = 0;

    public ConnectRequestView(JFrame frame, User currentUser, UserSession session) {
        List<User> requests = session.getIncomingMatches();

        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.setBackground(Color.WHITE);

        // Top bar
        JLabel title = new JLabel("Connect Request", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel countLabel = new JLabel("", SwingConstants.CENTER);
        countLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton back = new JButton("← Back");
        back.addActionListener(e -> {
            frame.setContentPane(new MatchingRoomView(frame, currentUser, session.getAllUsers(), session));
            frame.revalidate();
        });

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.add(back, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        topBar.add(countLabel, BorderLayout.EAST);

        this.add(topBar, BorderLayout.NORTH);

        //Card panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        cardPanel.setBackground(Color.WHITE);

        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        JLabel info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel score = new JLabel("50%", SwingConstants.CENTER); // 可自定义
        score.setFont(new Font("Arial", Font.BOLD, 24));
        score.setForeground(new Color(0x2E8B57));

        cardPanel.add(profilePic, BorderLayout.WEST);
        cardPanel.add(info, BorderLayout.CENTER);
        cardPanel.add(score, BorderLayout.EAST);

        this.add(cardPanel, BorderLayout.CENTER);

        //Action buttons
        JPanel actionPanel = new JPanel();
        JButton acceptBtn = new JButton("accept");
        JButton declineBtn = new JButton("decline");

        acceptBtn.setBackground(new Color(0x4CAF50));
        acceptBtn.setForeground(Color.WHITE);

        declineBtn.setBackground(new Color(0xF44336));
        declineBtn.setForeground(Color.WHITE);

        actionPanel.add(acceptBtn);
        actionPanel.add(declineBtn);

        this.add(actionPanel, BorderLayout.SOUTH);

        //Display logic
        Runnable updateCard = () -> {
            if (currentIndex >= requests.size()) {
                info.setText("No more requests.");
                score.setText("");
                countLabel.setText("");
                acceptBtn.setEnabled(false);
                declineBtn.setEnabled(false);
                return;
            }

            User other = requests.get(currentIndex);
            info.setText(
                    "<html><b>" + other.getName() + "</b><br/>"
                            + other.getAge() + "<br/>"
                            + other.getLocation() + "<br/><i>"
                            + other.getBio() + "</i></html>"
            );
            countLabel.setText((currentIndex + 1) + " / " + requests.size());
        };

        acceptBtn.addActionListener(e -> {
            User other = requests.get(currentIndex);
            currentUser.getFriendList().add(other);
            other.getFriendList().add(currentUser);
            session.addMatch(new Match(currentUser, other));
            session.getIncomingMatches().remove(other);
            currentIndex++;
            updateCard.run();
        });

        declineBtn.addActionListener(e -> {
            currentIndex++;
            updateCard.run();
        });

        updateCard.run();
    }
}
