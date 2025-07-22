package view;

import app.createPost.CreatePostInteractor;
import entities.UserSession;
import interface_adapter.controller.PostFeedViewController;
import view.components.CircularButton;

import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;

/**
 * View for the post feed panel.
 * */
public class PostFeedView {
    public static JPanel create(PostFeedViewController controller) {
        JPanel panel = new JPanel();

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));



        // Create a JPanel for title
        JPanel title = new JPanel();
        title.setSize(300, 25);
        JLabel titleLabel = new JLabel("Post Feed");
        title.add(titleLabel);


        // Create a layered pane to hold postFeed panel and overlay panel
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(300, 250));

        // Create and size the postFeed panel
        JPanel postFeed = new JPanel();
        postFeed.setLayout(new BoxLayout(postFeed, BoxLayout.Y_AXIS));
        postFeed.setBounds(0, 0, 300, 250);
        postFeed.setBackground(Color.LIGHT_GRAY);
        postFeed.setOpaque(true);

        // add mock "post cards" into it
        for (int i = 1; i <= 6; i++) {
            JPanel postCard = getPost(i);

            postFeed.add(Box.createVerticalStrut(10));  // space between cards
            postFeed.add(postCard);
        }

        // If your post feed grows tall, wrap it in a scroll pane
        JScrollPane scrollPane = new JScrollPane(postFeed);
        scrollPane.setBounds(0, 0, 300, 250);  // same bounds as postFeed originally
        scrollPane.setBorder(null);

        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);


//
//
//        // Add postFeed to bottom layer
//        layeredPane.add(postFeed, JLayeredPane.DEFAULT_LAYER);

        // Create overlay panel with null layout
        JPanel newButtonPanel = new JPanel(null);
        newButtonPanel.setOpaque(false);
        newButtonPanel.setBounds(0, 0, 300, 250);

        // Create the circular button
        CircularButton newPost = new CircularButton("New");
        newPost.setSize(40, 40);  // explicitly set size
        newPost.setBackground(new Color(161, 220, 136));  // Light green
        newPost.setForeground(Color.BLACK);              // Text color
        newPost.setBorderPainted(false);


        // Temporarily place it at (0,0), we'll reposition after layout
        newPost.setLocation(0, 0);
        newButtonPanel.add(newPost);

        // Add overlay panel to top layer
        layeredPane.add(newButtonPanel, JLayeredPane.PALETTE_LAYER);

        // Reposition button on resize
        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                int padding = 20;
                int x = newButtonPanel.getWidth() - newPost.getWidth() - padding - 20;
                int y = newButtonPanel.getHeight() - newPost.getHeight() - padding;
                newPost.setLocation(x, y);
            }
        });



        // Create a JPanel for three tabs buttons
        JPanel tabs = new JPanel();
        tabs.setLayout(new BoxLayout(tabs, BoxLayout.X_AXIS));

        // Create buttons
        JButton btnMatching = new JButton("Matching");
        JButton btnShare = new JButton("Share");
        JButton btnProfile = new JButton("My Profile");

        // Add buttons to tabs with some spacing in between
        tabs.add(btnMatching);
        tabs.add(Box.createRigidArea(new Dimension(10, 0)));  // horizontal gap
        tabs.add(btnShare);
        tabs.add(Box.createRigidArea(new Dimension(10, 0)));
        tabs.add(btnProfile);

        panel.add(title);
        panel.add(layeredPane);
        panel.add(tabs);

        // Define what happens when the button newPost is clicked
        newPost.addActionListener(
                e -> {
                    try {
                        controller.createNewPost();
                        JOptionPane.showMessageDialog(panel, "Redirecting...");
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(panel, "Error occurred.");
                    }
                });

        return panel;
    }

    private static JPanel getPost(int i) {
        JPanel postCard = new JPanel();
        postCard.setPreferredSize(new Dimension(250, 40));  // slightly narrower than postFeed
        postCard.setMaximumSize(new Dimension(250, 40));    // important for BoxLayout
        postCard.setBackground(new Color(255, 255, 255));

        JLabel label = new JLabel("Post " + i);
        postCard.add(label);
        return postCard;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        final JFrame frame = new JFrame("post feed");
        frame.setSize(300, 330);
        JPanel view;

        CreatePostInteractor interactor = new CreatePostInteractor();
        PostFeedViewController controller = new PostFeedViewController(interactor);

        // Shared user session across the app
        UserSession session = new UserSession();

        // Post Feed Setup
        PostFeedView viewSetup = new PostFeedView();
        view = viewSetup.create(controller);

        // Initial Login View
        frame.add(view);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

