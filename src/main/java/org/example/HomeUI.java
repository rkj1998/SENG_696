package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The HomeUI class represents the user interface for the home screen of the healthcare appointment system.
 */
public class HomeUI extends UI implements ActionListener {
    JLabel welcomeTextLabel = new JLabel();
    JButton pastAPPButton = new JButton("Past Appointments");
    JComboBox<String> specializationDropdown = new JComboBox<>();
    JButton showSpecialistsButton = new JButton("Show Specialists");

    private static HomeUI singleton = null;
    JPanel panel = new JPanel();

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param frameTitle Title of the frame.
     */
    private HomeUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        pastAPPButton.addActionListener(this);

        // Add the specialization dropdown
        populateSpecializationDropdown(); // Populate the dropdown
        panel.add(specializationDropdown);
        // Add a button to trigger displaying specialists
        showSpecialistsButton.addActionListener(this);
        panel.add(showSpecialistsButton);
        panel.add(pastAPPButton);

        panel.setLayout(new FlowLayout());
        panel.add(welcomeTextLabel);

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.add(panel);
        frame.pack();
    }

    /**
     * Populate the specialization dropdown with predefined values.
     */
    private void populateSpecializationDropdown() {
        specializationDropdown.addItem("Neuroscience");
        specializationDropdown.addItem("Cardiology");
        specializationDropdown.addItem("Orthopedics");
        specializationDropdown.addItem("Dermatology");
        specializationDropdown.addItem("Ophthalmology");
    }

    /**
     * Create or retrieve the singleton instance of HomeUI.
     *
     * @return The singleton instance of HomeUI.
     */
    public static HomeUI createUI() {
        if (singleton == null) {
            singleton = new HomeUI("Home");
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
        if (e.getSource() == pastAPPButton) {
            // Trigger action to request past appointment history
            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestPastAppointments();
        } else if (e.getSource() == showSpecialistsButton) {
            // Trigger action to request specialist list based on selected specialization from the dropdown
            String selectedSpecialization = (String) specializationDropdown.getSelectedItem();

            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestSpecialistList(selectedSpecialization); // Pass the selected specialization
        }
    }

    /**
     * Set the welcome text to display the user's name.
     *
     * @param name The user's name.
     */
    public void setName(String name) {
        welcomeTextLabel.setText("Welcome user " + name);
    }
}
