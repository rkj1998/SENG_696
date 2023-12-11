package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PastAppointmentsUI class represents the user interface for viewing past appointments in the healthcare appointment system.
 */
public class PastAppointmentsUI extends UI implements ActionListener {
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
                panel.setLayout(new FlowLayout());

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        goBackHome.addActionListener(this);
        frame.add(goBackHome);
        frame.add(panel);
    }


    public void show(String[] data) {
        appointmentDataHandler(data);  // Handle the appointment data
        frame.pack();  // Pack the frame
        frame.setLocationRelativeTo(null);  // Center the frame on the screen
        frame.setVisible(true);  // Set the frame visibility to true
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
