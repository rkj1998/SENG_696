package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUI extends UI implements ActionListener  {
    JLabel welcomeTextLabel = new JLabel();
    JButton pastAPPButton = new JButton("Past Appointments");
    JComboBox<String> specializationDropdown = new JComboBox<>();
    JButton showSpecialistsButton = new JButton("Show Specialists");

    private static HomeUI singleton = null;
    JPanel panel = new JPanel();

    private HomeUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        panel.add(pastAPPButton);
        pastAPPButton.addActionListener(this);

        // Add the specialization dropdown
        populateSpecializationDropdown(); // Populate the dropdown
        panel.add(specializationDropdown);

        // Add a button to trigger displaying specialists
        showSpecialistsButton.addActionListener(this);
        panel.add(showSpecialistsButton);

        panel.setLayout(new FlowLayout());
        panel.add(welcomeTextLabel);

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.add(panel);
        frame.pack();
    }

    private void populateSpecializationDropdown() {
        specializationDropdown.addItem("Neuroscience");
        specializationDropdown.addItem("Cardiology");
        specializationDropdown.addItem("Orthopedics");
        specializationDropdown.addItem("Dermatology");
        specializationDropdown.addItem("Ophthalmology");
    }

    public static HomeUI createUI() {
        if (singleton == null) {
            singleton = new HomeUI("Home");
        }
        return singleton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pastAPPButton) {
            this.disposeFrame();
            // TODO: ask for past appointment history
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestPastAppointments("Temp name"); // TODO: change temp name to user name!
        } else if (e.getSource() == showSpecialistsButton) {
            // Get the selected specialization from the dropdown
            String selectedSpecialization = (String) specializationDropdown.getSelectedItem();

            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestSpecialistList(selectedSpecialization); // Pass the selected specialization
        }
    }
    public void setName(String name) {
        welcomeTextLabel.setText("Welcome user " + name);
    }
}
