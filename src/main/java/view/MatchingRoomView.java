package view;

import app.individual_story.CreatePostInteractor;
import entities.User;
import entities.UserSession;
import interface_adapter.controller.PostFeedController;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class MatchingRoomView extends JPanel {

    private int currentIndex = 0;

    public MatchingRoomView(
            JFrame frame, User currentUser, List<User> matches, UserSession session) {

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 600));
//        this.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
//        this.setBackground(Color.WHITE);

        // Jpanel for Title
        JLabel title = new JLabel("Matching Room", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
//        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // mail icon
        JLabel mailIcon = new JLabel("\u2709", SwingConstants.LEFT);
        mailIcon.setFont(new Font("Arial", Font.PLAIN, 24));
        mailIcon.setForeground(Color.DARK_GRAY);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(mailIcon, BorderLayout.WEST);
        topPanel.add(title, BorderLayout.CENTER);


        // ConnectRequestView
        mailIcon.addMouseListener(
                new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e) {
                        frame.setContentPane(new ConnectRequestView(frame, currentUser, session));
                        frame.revalidate();
                        frame.repaint();
                    }
                });



        // User card panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // matched user's profile picture
        JLabel profilePic = new JLabel("?", SwingConstants.CENTER);
        profilePic.setFont(new Font("Arial", Font.PLAIN, 64));
        profilePic.setPreferredSize(new Dimension(120, 120));

        // matched user's profile
        JLabel profileInfo = new JLabel("", SwingConstants.CENTER);
        profileInfo.setFont(new Font("Arial", Font.PLAIN, 16));

        // match compatibility score
        JLabel score = new JLabel("", SwingConstants.CENTER);
        score.setFont(new Font("Arial", Font.BOLD, 28));
        score.setForeground(new Color(0x2E8B57));

        JPanel innerCard = new JPanel(new GridLayout(1, 3));    // made each take up 1/3 rather than 1/2
//        innerCard.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        // Existing visible border
        Border visibleBorder = BorderFactory.createLineBorder(Color.GRAY, 2);
        // Add empty padding around it (top, left, bottom, right)
        Border padding = BorderFactory.createEmptyBorder(30, 50, 30, 50);
        // Combine both
        innerCard.setBorder(BorderFactory.createCompoundBorder(padding, visibleBorder));

//        JPanel right = new JPanel(new BorderLayout());
//        right.add(profileInfo, BorderLayout.CENTER);
//        right.add(score, BorderLayout.EAST);
//        innerCard.add(right);

        innerCard.add(profilePic);
        innerCard.add(profileInfo);
        innerCard.add(score);

        mainPanel.add(innerCard, BorderLayout.CENTER);


        // connect / skip buttons
        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new BorderLayout());
//        actionPanel.setLayout(new BoxLayout(actionPanel, BoxLayout.X_AXIS));
//        actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        actionPanel.setOpaque(false); // transparent background

        JButton connectBtn = createStyledButton("connect", new Color(0x4CAF50));
        JButton skipBtn = createStyledButton("skip", new Color(0xF44336));

        JPanel leftButtonPanel = new JPanel();
        leftButtonPanel.add(connectBtn);
        JPanel rightButtonPanel = new JPanel();
        rightButtonPanel.add(skipBtn);

        leftButtonPanel.setBorder(padding);
        rightButtonPanel.setBorder(padding);

//        actionPanel.add(Box.createHorizontalGlue());
//        actionPanel.add(connectBtn);
//        actionPanel.add(Box.createHorizontalStrut(20)); // spacing between buttons
//        actionPanel.add(skipBtn);
//        actionPanel.add(Box.createHorizontalGlue());

        actionPanel.add(leftButtonPanel, BorderLayout.WEST);
        actionPanel.add(rightButtonPanel, BorderLayout.EAST);

        // nav bar
        JPanel navPanel = new JPanel(new GridLayout(1, 3));
        navPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JButton matchingBtn = new JButton("Matching");
        JButton shareBtn = new JButton("Share");
        JButton yourProfileBtn = new JButton("My Profile");
        navPanel.add(matchingBtn);
        navPanel.add(shareBtn);
        navPanel.add(yourProfileBtn);

        // Bottom control panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
//        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

//        actionPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));
//        navPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        bottomPanel.add(actionPanel);
        bottomPanel.add(Box.createVerticalStrut(10));
        bottomPanel.add(navPanel);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH); // ✅ ONLY ONE add to BorderLayout.SOUTH

        // display logic
        Runnable updateDisplay = () -> {
            if (currentIndex >= matches.size()) {
                profileInfo.setText("No more matches.");
                score.setText("");
                connectBtn.setEnabled(false);
                skipBtn.setEnabled(false);
                return;
            }
            User match = matches.get(currentIndex);
            profileInfo.setText(
                    "<html><b>" + match.getName() + "</b><br/>"
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

        yourProfileBtn.addActionListener(e -> {
            frame.setContentPane(new ProfileView(currentUser, frame, session));
            frame.revalidate();
            frame.repaint();
        });

        shareBtn.addActionListener(e -> {
            PostFeedController controller = new PostFeedController(new CreatePostInteractor());
            JPanel postFeedPanel = new PostFeedView(currentUser, session, frame).create(controller);
            frame.setContentPane(postFeedPanel);
            frame.revalidate();
            frame.repaint();
        });


        updateDisplay.run();
    }

    private static JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 40));
        button.setMaximumSize(new Dimension(120, 40));
        return button;
    }

    public static void showInFrame(User currentUser, List<User> matches) {
        JFrame frame = new JFrame("JRMC Matching Room");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLocationRelativeTo(null);

        // build a dummy session to avoid null
        UserSession dummySession = new UserSession();
        dummySession.setUser(currentUser);

        MatchingRoomView view = new MatchingRoomView(frame, currentUser, matches, dummySession);
        frame.setContentPane(view);
        frame.setVisible(true);
    }
}
