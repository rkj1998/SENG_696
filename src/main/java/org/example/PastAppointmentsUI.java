package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PastAppointmentsUI class represents the user interface for viewing past appointments in the healthcare appointment system.
 */
public class PastAppointmentsUI extends UI implements ActionListener {
    JTextField descriptionTextField = new JTextField(20);
    JPanel panel = new JPanel();
    JList<String> appointmentData;
    JButton goBackHome = new JButton("Home");
    private static PastAppointmentsUI singleton = null;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param frameTitle Title of the frame.
     */
    private PastAppointmentsUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        descriptionTextField.setPreferredSize(new Dimension(250, 40));
        descriptionTextField.setText("Click on the specialist list to see the list of available specialists and make an appointment. " +
                "You can see the history of your past appointments by pressing the past appointments button.");
        panel.setLayout(new FlowLayout());
        panel.add(descriptionTextField);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        goBackHome.addActionListener(this);
        frame.add(panel);
    }

    /**
     * Dummy implementation for the show method.
     */
    @Override
    public void show() {
        // Dummy implementation
    }

    /**
     * Display the frame with specified data.
     *
     * @param data The data to be displayed.
     */
    public void show(String[] data) {
        frame.setVisible(true);
    }

    /**
     * Create or retrieve the singleton instance of PastAppointmentsUI.
     *
     * @return The singleton instance of PastAppointmentsUI.
     */
    public static PastAppointmentsUI createUI() {
        if (singleton == null) {
            singleton = new PastAppointmentsUI("past");
        }
        return singleton;
    }

    /**
     * Handle the appointment data and display it in the frame.
     *
     * @param data The array of appointment data to be displayed.
     */
    public void appointmentDataHandler(String[] data) {
        appointmentData = new JList<>(data);
        frame.add(appointmentData);
        frame.pack();
    }

    /**
     * Handle button click events.
     *
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.goBackHome) {
            // Return to the home screen
            HomeUI homeUIInstance = HomeUI.createUI();
            homeUIInstance.show();
        }
    }
}
