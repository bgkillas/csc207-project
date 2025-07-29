package view;

import account.login.SetupUserProfileController;
import entities.User;

import javax.swing.*;
import java.awt.*;

/**
 * This class builds the GUI form for setting up a user profile It collects user input (bio, age,
 * gender, location) and passes it to the controller
 */
public class ProfileSetupView {
    private ProfileSetupView() {}

    /**
     * Creates and returns a JPanel containing profile setup fields and a submit button
     *
     * @param controller The controller that handles setting up the user profile
     * @return A JPanel with input fields and a button to submit profile data
     */
    public static JPanel create(SetupUserProfileController controller, User user) {
        JPanel panel = new JPanel();
        JButton uploadButton = new JButton("Upload Profile Picture");
        panel.add(uploadButton);

        uploadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(panel);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    java.io.File file = chooser.getSelectedFile();
                    javax.imageio.ImageIO.setUseCache(false);
                    Image img = javax.imageio.ImageIO.read(file);
                    // Optionally scale/crop img here
                    user.setProfilePicture(img); // Save image to user
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(panel, "Failed to load image.");
                }
            }
        });

        JTextField bioField = new JTextField(20);
        JTextField ageField = new JTextField(3);
        JTextField genderField = new JTextField(10);
        JTextField locationField = new JTextField(20);
        JButton submit = new JButton("Submit Profile");

        panel.add(new JLabel("Bio:"));
        panel.add(bioField);
        panel.add(new JLabel("Age:"));
        panel.add(ageField);
        panel.add(new JLabel("Gender:"));
        panel.add(genderField);
        panel.add(new JLabel("Location:"));
        panel.add(locationField);
        panel.add(submit);

        // Define what happens when the submit button is clicked
        submit.addActionListener(
                e -> {
                    try {
                        String bio = bioField.getText();
                        int age = Integer.parseInt(ageField.getText());
                        String gender = genderField.getText();
                        String location = locationField.getText();
                        controller.setupUserProfile(bio, age, gender, location);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(panel, "Age must be a valid number.");
                    }
                });

        return panel;
    }
}
