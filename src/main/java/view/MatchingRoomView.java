package view;

import entities.User;
import entities.UserSession;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MatchingRoomView extends JPanel {

    private int currentIndex = 0;

    public MatchingRoomView(JFrame frame, User currentUser, List<User> matches, UserSession session) {
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        this.setBackground(Color.WHITE);

        // 🔝 Top title bar
        JPanel topBar = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Matching Room", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        topBar.add(title, BorderLayout.CENTER);

        // optional: top right mail icon + notification bubble
        JLabel notification = new JLabel("\u2709", SwingConstants.LEFT);
        notification.setFont(new Font("Arial", Font.PLAIN, 24));
        notification.setForeground(Color.DARK_GRAY);
        topBar.add(notification, BorderLayout.WEST);

        this.add(topBar, BorderLayout.NORTH);

        // 👤 User card panel
        JPanel cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        cardPanel.setBackground(Color.WHITE);

        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        JLabel info = new JLabel("", SwingConstants.CENTER);
        info.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel score = new JLabel("", SwingConstants.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 28));
        score.setForeground(new Color(0x2E8B57));

        JPanel innerCard = new JPanel(new GridLayout(1, 2));
        innerCard.add(profilePic);

        JPanel right = new JPanel(new BorderLayout());
        right.add(info, BorderLayout.CENTER);
        right.add(score, BorderLayout.EAST);
        innerCard.add(right);

        cardPanel.add(innerCard, BorderLayout.CENTER);
        this.add(cardPanel, BorderLayout.CENTER);

        // ✅ Buttons: connect / skip
        JPanel actionPanel = new JPanel(new FlowLayout());
        JButton connectBtn = new JButton("connect");
        JButton skipBtn = new JButton("skip");

        connectBtn.setBackground(new Color(0x4CAF50));
        connectBtn.setForeground(Color.WHITE);
        skipBtn.setBackground(new Color(0xF44336));
        skipBtn.setForeground(Color.WHITE);

        actionPanel.add(connectBtn);
        actionPanel.add(skipBtn);
        this.add(actionPanel, BorderLayout.SOUTH);

        // 🔻 Bottom nav bar
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton matchingBtn = new JButton("matching");
        JButton shareBtn = new JButton("share");
        JButton yourProfileBtn = new JButton("your profile");
        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(yourProfileBtn);

        this.add(navPanel, BorderLayout.PAGE_END);

        // 👇 Display logic
        Runnable updateDisplay = () -> {
            if (currentIndex >= matches.size()) {
                info.setText("No more matches.");
                score.setText("");
                connectBtn.setEnabled(false);
                skipBtn.setEnabled(false);
                return;
            }

            User match = matches.get(currentIndex);
            info.setText("<html><b>" + match.getName() + "</b><br/>"
                    + match.getAge() + "<br/>"
                    + match.getLocation() + "<br/>"
                    + "\"" + match.getBio() + "\"</html>");
            score.setText("97%");
        };

        connectBtn.addActionListener(e -> {
            currentUser.getFriendList().add(matches.get(currentIndex));
            currentIndex++;
            updateDisplay.run();
        });

        skipBtn.addActionListener(e -> {
            currentIndex++;
            updateDisplay.run();
        });

        // trigger profile view when button is pressed
        yourProfileBtn.addActionListener(e -> {
            frame.setContentPane(new ProfileView(currentUser, frame, session));
            frame.revalidate();
            frame.repaint();
        });

        updateDisplay.run();
    }

    public static void showInFrame(User currentUser, List<User> matches) {
        JFrame frame = new JFrame("JRMC Matching Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        MatchingRoomView view = new MatchingRoomView(frame, currentUser, matches, null);
        frame.setContentPane(view);
        frame.setVisible(true);
    }
}
