package app;

import account.login.LoginManager;
import account.login.LoginManagerMemory;
import java.security.NoSuchAlgorithmException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import view.LoginView;

/** Main executable class. */
public class Main {
    /** Main executable point. */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        final JFrame application = new JFrame("app");
        final JPanel views = new JPanel();
        final LoginManager login_manager = new LoginManagerMemory();
        final JPanel login = LoginView.create(login_manager);
        views.add(login);
        application.add(views);
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setVisible(true);
    }
}
