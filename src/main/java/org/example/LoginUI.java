package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The LoginUI class represents the user interface for the login screen of the healthcare appointment system.
 */
public class LoginUI extends UI implements ActionListener {

    private JTextField emailTextField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton submitButton = new JButton("Submit");

    private static LoginUI singleton = null;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param frameTitle Title of the frame.
     */
    private LoginUI(String frameTitle) {
        super(frameTitle);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(createLabel("Email:"), gbc);

        gbc.gridy++;
        panel.add(emailTextField, gbc);

        gbc.gridy++;
        panel.add(createLabel("Password:"), gbc);

        gbc.gridy++;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(this);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Create a label with specified text.
     *
     * @param text The text for the label.
     * @return The created label.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(80, 20));
        return label;
    }

    /**
     * Create or retrieve the singleton instance of LoginUI.
     *
     * @return The singleton instance of LoginUI.
     */
    public static LoginUI createUI() {
        if (singleton == null) {
            singleton = new LoginUI("Login");
        }
        return singleton;
    }

    /**
     * Handle button click events.
     *
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Retrieve email and password input
            String email = emailTextField.getText();
            String password = new String(passwordField.getPassword());

            // Validate email using regular expression
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(email);
            boolean doesItMatch = matcher.matches();

            // Show error messages if validation fails
            if (!doesItMatch) {
                this.showFailureEmail();
            }
            if (password.length() < 3 || password.length() > 10) {
                this.showFailurePassword();
            }

            // Request login using the provided credentials
            PortalGUI.returnSingleton().requestLoginUser(email, password);
        }
    }

    /**
     * Display a failure message for general login failure.
     */
    public void showFailureMessage() {
        JOptionPane.showMessageDialog(null, "Login failed", "alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display a failure message for invalid email.
     */
    public void showFailureEmail() {
        JOptionPane.showMessageDialog(null, "Login failed because email is invalid", "alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display a failure message for invalid password length.
     */
    public void showFailurePassword() {
        JOptionPane.showMessageDialog(null, "Login failed because password is too short (<3) or too long(>10)", "alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display a success message for a successful login.
     */
    public void showSuccessLogin() {
        JOptionPane.showMessageDialog(null, "Login successful! Welcome!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }
}
