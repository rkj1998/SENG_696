package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessUI extends UI implements ActionListener {

    private JLabel titleLabel = createStyledLabel("Multi-Agent System for Healthcare Appointment Scheduling", 18, Font.BOLD);
    private JLabel projectLabel = createStyledLabel("Final Project", 16, Font.BOLD);
    private JLabel courseLabel = createStyledLabel("Agent-based Software Engineering (SENG696)", 14, Font.PLAIN);
    private JLabel groupLabel = createStyledLabel("Group 5", 14, Font.PLAIN);
    private JLabel membersLabel = createStyledLabel("Rahul Jha, Narges Babadi", 14, Font.PLAIN);

    private JLabel descriptionTextField = createStyledLabel("Welcome to the Specialist Platform", 16, Font.PLAIN);
    private JButton loginButton = createStyledButton("Login", 14);
    private JButton registerButton = createStyledButton("Register", 14);

    private static AccessUI singleton = null;

    private AccessUI(String frameTitle) {
        super(frameTitle);
        initUI();
    }

    public static AccessUI createUI() {
        if (singleton == null) {
            singleton = new AccessUI("Specialist Platform");
        }
        return singleton;
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(500, 300));

        JPanel headerPanel = new JPanel(new GridLayout(5, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(projectLabel);
        headerPanel.add(courseLabel);
        headerPanel.add(groupLabel);
        headerPanel.add(membersLabel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(descriptionTextField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.frame.add(mainPanel);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    private JLabel createStyledLabel(String text, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, fontStyle, fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JButton createStyledButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.loginButton) {
            // open window for login
            this.disposeFrame();
            LoginUI myLoginWindow = LoginUI.createUI();
            myLoginWindow.show();
        } else if (e.getSource() == this.registerButton) {
            // open window for register
            this.disposeFrame();
            RegisterUI myRegisterWindow = RegisterUI.createUI();
            myRegisterWindow.show();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccessUI accessUI = AccessUI.createUI();
            accessUI.show();
        });
    }
}
