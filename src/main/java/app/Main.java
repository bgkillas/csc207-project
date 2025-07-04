package java.app;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/** Main executable class. */
public class Main {
    /** Main executable point. */
    public static void main(String[] args) {
        final JFrame application = new JFrame("app");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.pack();
        application.setVisible(true);
    }
}
