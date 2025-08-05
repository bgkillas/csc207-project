package app.frameworks_and_drivers.view;

import app.frameworks_and_drivers.external.spotify.Spotify;
import app.interface_adapter.controller.CreateAccountController;
import app.usecase.login.LoginManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;

/** creates a login panel. */
public class LoginView {
    /**
     * creates a JPanel from the given LoginManager.
     *
     * @param loginManager a LoginManager to check if logins are valid
     */
    public static JPanel create(LoginManager loginManager, CreateAccountController controller) {
        final JPanel panel = new JPanel();
        // login fields
        final JButton logIn = new JButton("Continue with Spotify");
        panel.add(logIn);
        logIn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        controller.createAccount(new Spotify());
                    }
                });
        panel.setPreferredSize(new Dimension(800, 600));
        return panel;
    }
}
