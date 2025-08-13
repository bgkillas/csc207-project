package app.frameworks_and_drivers.view;

import app.frameworks_and_drivers.external.spotify.Spotify;
import app.interface_adapter.controller.CreateAccountController;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/** creates a login panel. */
public class LoginView {
    /**
     * creates a JPanel from the given LoginManager.
     */
    public static JPanel create(CreateAccountController controller) {
        final JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 600));
        panel.setLayout(new BorderLayout());

        // Outer panel in CENTER to center inner content
        JPanel centerWrapper = new JPanel(new GridBagLayout());

        // Inner content (title + subtitle)
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);

        JLabel title = new JLabel("S-Buddify");
        title.setFont(new Font("SansSerif", Font.PLAIN, 55));
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitle = new JLabel("share your music taste");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 30));
        subtitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(title);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(subtitle);

        centerWrapper.add(centerPanel);

        // Bottom panel with login button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 80, 40));

        // login button
        final JButton logIn = new JButton("Continue with Spotify");
        logIn.setBackground(new Color(100, 182, 100));
        logIn.setFont(new Font("SansSerif", Font.PLAIN, 17));
        logIn.setPreferredSize(new Dimension(300, 40));
        logIn.setMaximumSize(new Dimension(300, 40));
        logIn.setFocusPainted(false);

        bottomPanel.add(logIn);

        panel.add(centerWrapper, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        logIn.addActionListener(
                actionEvent -> controller.createAccount(new Spotify()));
        return panel;
    }
}
