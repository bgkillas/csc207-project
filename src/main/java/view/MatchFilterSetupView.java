package view;

import account.login.SetupMatchFilterController;

import javax.swing.*;
import java.awt.*;

/**
 * This class builds the GUI form for setting up a user's match filter preferences It collects user
 * input (min age, max age, preferred gender, and location), and sends it to the controller when
 * "Set Match Filter" button pressed
 */
public class MatchFilterSetupView {
    public static JPanel create(SetupMatchFilterController controller) {
        /**
         * Creates a JPanel that includes input fields and a submit button to set up the match
         * filter
         *
         * @param controller the controller that handles the setup of the match filter
         * @return a fully constructed JPanel for match filter setup
         */
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField minAgeField = new JTextField();
        JTextField maxAgeField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField locationField = new JTextField();
        JButton submit = new JButton("Set Match Filter");

        panel.add(new JLabel("Min Age:"));
        panel.add(minAgeField);
        panel.add(new JLabel("Max Age:"));
        panel.add(maxAgeField);
        panel.add(new JLabel("Preferred Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Preferred Location:"));
        panel.add(locationField);
        panel.add(submit);

        submit.addActionListener(
                e -> {
                    try {
                        int minAge = Integer.parseInt(minAgeField.getText());
                        int maxAge = Integer.parseInt(maxAgeField.getText());
                        String gender = genderField.getText();
                        String location = locationField.getText();
                        controller.setupFilter(minAge, maxAge, gender, location);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Please enter valid age values.");
                    }
                });

        return panel;
    }
}
