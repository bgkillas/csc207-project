package view;

import account.login.CreateAccountController;
import account.login.LoginManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
        final JTextField username = new JTextField(16);
        panel.add(username);
        final JTextField password = new JTextField(16);
        panel.add(password);
        final JButton logIn = new JButton("log in");
        panel.add(logIn);
        logIn.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent actionEvent) {
                        if (actionEvent.getSource().equals(logIn)) {
                            String user = username.getText();
                            String pass = password.getText();
                            if (user.isEmpty() || pass.isEmpty()) {
                                JOptionPane.showMessageDialog(panel, "Login failed");
                            } else if (loginManager.hasLogin(user)) {
                                if (loginManager.tryLogin(user, pass)) {
                                    JOptionPane.showMessageDialog(panel, "Login success");
                                } else {
                                    JOptionPane.showMessageDialog(panel, "Login failed");
                                }
                            } else {
                                loginManager.registerLogin(user, pass);
                                JOptionPane.showMessageDialog(panel, "Login created");
                            }
                        }
                    }
                });

        // Spotify sign-up fields
        final JTextField spotifyUsernameField = new JTextField(16);
        panel.add(spotifyUsernameField);
        final JButton signUp = new JButton("Sign Up with Spotify");
        panel.add(signUp);

        signUp.addActionListener(
                e -> {
                    String spotifyUsername = spotifyUsernameField.getText();
                    if (spotifyUsername.isEmpty()) {
                        JOptionPane.showMessageDialog(panel, "Please enter a Spotify username.");
                    } else {
                        controller.createAccount(
                                spotifyUsername); // Presenter handles success message
                    }
                });
        return panel;
    }
}
