package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The RegisterUI class represents the user interface for user registration.
 */
public class RegisterUI extends UI implements ActionListener {
    private JTextField emailTextField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JTextField nameTextField = new JTextField(20);
    private JTextField phoneTextField = new JTextField(20);
    private JButton submitButton = new JButton("Submit");

    private static RegisterUI singleton = null;

    /**
     * Private constructor to create a RegisterUI instance.
     *
     * @param frameTitle The title of the frame.
     */
    private RegisterUI(String frameTitle) {
        super(frameTitle);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setPreferredSize(new Dimension(300, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(createLabel("Name:"), gbc);

        gbc.gridy++;
        panel.add(nameTextField, gbc);

        gbc.gridy++;
        panel.add(createLabel("Email:"), gbc);

        gbc.gridy++;
        panel.add(emailTextField, gbc);

        gbc.gridy++;
        panel.add(createLabel("Password:"), gbc);

        gbc.gridy++;
        panel.add(passwordField, gbc);

        gbc.gridy++;
        panel.add(createLabel("Phone:"), gbc);

        gbc.gridy++;
        panel.add(phoneTextField, gbc);

        gbc.gridy++;
        panel.add(submitButton, gbc);

        submitButton.addActionListener(this);

        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }

    /**
     * Creates and returns a JLabel with the specified text.
     *
     * @param text The text for the label.
     * @return The JLabel with the specified text.
     */
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setPreferredSize(new Dimension(80, 20));
        return label;
    }

    /**
     * Handles the action performed event, triggered when the submit button is clicked.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            String email = emailTextField.getText();
            String name = nameTextField.getText();
            String password = new String(passwordField.getPassword());
            String phone = phoneTextField.getText();

            // Validation logic
            Pattern emailPattern = Pattern.compile("^.+@.+\\..+$");
            Matcher emailMatcher = emailPattern.matcher(email);
            boolean emailIsValid = emailMatcher.matches();

            Pattern phonePattern = Pattern.compile("^\\d{10}$");
            Matcher phoneMatcher = phonePattern.matcher(phone);
            boolean phoneIsValid = phoneMatcher.matches();

            if (name.length() < 3 || name.length() > 10) {
                showFailureName();
                return;
            }
            if (!emailIsValid) {
                showFailureEmail();
                return;
            }
            if (password.length() < 3 || password.length() > 10) {
                showFailurePassword();
                return;
            }
            if (!phoneIsValid) {
                showFailurePhone();
                return;
            }

            // If all validations pass, proceed with registration
            PortalGUI.returnSingleton().requestRegister(name, email, phone, password);
            showSuccessRegister();
        }
    }

    /**
     * Displays a failure message for an invalid name.
     */
    public void showFailureName() {
        JOptionPane.showMessageDialog(null, "Registration failed because name is invalid", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a failure message for an invalid email.
     */
    public void showFailureEmail() {
        JOptionPane.showMessageDialog(null, "Registration failed because email is invalid", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a failure message for an invalid password.
     */
    public void showFailurePassword() {
        JOptionPane.showMessageDialog(null, "Registration failed because password is invalid", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a failure message for an invalid phone number.
     */
    public void showFailurePhone() {
        JOptionPane.showMessageDialog(null, "Registration failed because phone number is invalid", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Creates a singleton instance of RegisterUI.
     *
     * @return The singleton instance of RegisterUI.
     */
    public static RegisterUI createUI() {
        if (singleton == null) {
            singleton = new RegisterUI("Register");
        }
        return singleton;
    }

    /**
     * Displays a failure message for a general registration failure.
     */
    public void showFailureMessage() {
        JOptionPane.showMessageDialog(null, "Registration failed", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a success message for a successful registration.
     */
    public void showSuccessRegister() {
        JOptionPane.showMessageDialog(null, "Registration successful! Please log in.", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Main method to launch the RegisterUI.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegisterUI registerUI = RegisterUI.createUI();
            registerUI.show();
        });
    }
}
