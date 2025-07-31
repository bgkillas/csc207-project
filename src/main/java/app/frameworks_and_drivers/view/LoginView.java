package app.frameworks_and_drivers.view;

import app.interface_adapter.controller.CreateAccountController;
import app.usecase.login.LoginManager;
import java.awt.*;
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
        final JButton logIn = new JButton("Continue with Spotify");
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
                                    controller.createAccount(user);
                                } else {
                                    JOptionPane.showMessageDialog(panel, "Login failed");
                                }
                            } else {
                                loginManager.registerLogin(user, pass);
                                JOptionPane.showMessageDialog(panel, "Login created");
                                password.setText("");
                            }
                        }
                    }
                });
        panel.setPreferredSize(new Dimension(800, 600));
        return panel;
    }
}
