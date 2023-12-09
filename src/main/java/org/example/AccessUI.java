package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * AccessUI class represents the user interface for the Specialist Platform in a Multi-Agent System for Healthcare Appointment Scheduling.
 */
public class AccessUI extends UI implements ActionListener {

    // Labels for project information and group details
    private JLabel titleLabel = createStyledLabel("Multi-Agent System for Healthcare Appointment Scheduling", 18, Font.BOLD);
    private JLabel projectLabel = createStyledLabel("Final Project", 16, Font.BOLD);
    private JLabel courseLabel = createStyledLabel("Agent-based Software Engineering (SENG696)", 14, Font.PLAIN);
    private JLabel groupLabel = createStyledLabel("Group 5", 14, Font.PLAIN);
    private JLabel membersLabel = createStyledLabel("Rahul Jha, Narges Babadi", 14, Font.PLAIN);

    // Labels and buttons for login and registration
    private JLabel descriptionTextField = createStyledLabel("Welcome to the Specialist Platform", 16, Font.PLAIN);
    private JButton loginButton = createStyledButton("Login", 14);
    private JButton registerButton = createStyledButton("Register", 14);

    // Singleton instance
    private static AccessUI singleton = null;

    /**
     * Private constructor to enforce singleton pattern.
     * @param frameTitle Title of the frame.
     */
    private AccessUI(String frameTitle) {
        super(frameTitle);
        initUI();
    }

    /**
     * Create or retrieve the singleton instance of AccessUI.
     * @return The singleton instance of AccessUI.
     */
    public static AccessUI createUI() {
        if (singleton == null) {
            singleton = new AccessUI("Specialist Platform");
        }
        return singleton;
    }

    /**
     * Initialize the user interface components.
     */
    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setPreferredSize(new Dimension(500, 300));

        // Header panel with project information and group details
        JPanel headerPanel = new JPanel(new GridLayout(5, 1));
        headerPanel.add(titleLabel);
        headerPanel.add(projectLabel);
        headerPanel.add(courseLabel);
        headerPanel.add(groupLabel);
        headerPanel.add(membersLabel);

        // Panel for login and register buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        loginButton.addActionListener(this);
        registerButton.addActionListener(this);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Assemble the main panel
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(descriptionTextField, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add the main panel to the frame
        this.frame.add(mainPanel);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null); // Center the frame on the screen
    }

    /**
     * Create a styled label with specified text, font size, and style.
     * @param text The label text.
     * @param fontSize The font size.
     * @param fontStyle The font style.
     * @return The created JLabel.
     */
    private JLabel createStyledLabel(String text, int fontSize, int fontStyle) {
        JLabel label = new JLabel(text);
        label.setFont(new Font(Font.SANS_SERIF, fontStyle, fontSize));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Create a styled button with specified text and font size.
     * @param text The button text.
     * @param fontSize The font size.
     * @return The created JButton.
     */
    private JButton createStyledButton(String text, int fontSize) {
        JButton button = new JButton(text);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, fontSize));
        return button;
    }

    /**
     * Handle button click events.
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.loginButton) {
            // Open window for login
            this.disposeFrame();
            LoginUI myLoginWindow = LoginUI.createUI();
            myLoginWindow.show();
        } else if (e.getSource() == this.registerButton) {
            // Open window for register
            this.disposeFrame();
            RegisterUI myRegisterWindow = RegisterUI.createUI();
            myRegisterWindow.show();
        }
    }

    /**
     * Main method to launch the AccessUI.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccessUI accessUI = AccessUI.createUI();
            accessUI.show();
        });
    }
}
