package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class LoginUI extends UI implements ActionListener {


    private JTextField emailTextField = new JTextField(20);
    private JPasswordField passwordField = new JPasswordField(20);
    private JButton submitButton = new JButton("Submit");

    private static LoginUI singleton = null;

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

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setPreferredSize(new Dimension(80, 20));
        return label;
    }

    public static LoginUI createUI() {
        if (singleton == null) {
            singleton = new LoginUI("Login");
        }
        return singleton;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton)
        {
            String email = emailTextField.getText();
            String password = new String(passwordField.getPassword());

//            System.out.println(passwordTextField.getText());
            // regex from https://stackoverflow.com/questions/624581/what-is-the-best-java-email-address-validation-method
            Pattern pattern = Pattern.compile("^.+@.+\\..+$");
            Matcher matcher = pattern.matcher(email);
            boolean doesItMatch = matcher.matches();
            if (!doesItMatch)
            {
                this.showFailureEmail();
            }
            if (password.length() < 3 || password.length() > 10)
            {
                this.showFailurePassword();
            }

            PortalGUI.returnSingleton().requestLoginUser(email, password);
        }

    }

    public void showFailureMessage()
    {
        JOptionPane.showMessageDialog(null, "Login failed", "alert", JOptionPane.ERROR_MESSAGE);

    }

    public void showFailureEmail()
    {
        JOptionPane.showMessageDialog(null, "Login failed because email is invalid", "alert", JOptionPane.ERROR_MESSAGE);

    }
    public void showFailurePassword()
    {
        JOptionPane.showMessageDialog(null, "Login failed because password is too short (<3) or too long(>8)", "alert", JOptionPane.ERROR_MESSAGE);

    }
    public void showSuccessLogin()
    {
        JOptionPane.showMessageDialog(null, "Login successful! welcome!", "Success!", JOptionPane.INFORMATION_MESSAGE);

    }
}
