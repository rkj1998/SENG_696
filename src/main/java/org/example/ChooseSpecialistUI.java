package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ChooseSpecialistUI class represents the user interface for choosing a healthcare specialist.
 */
public class ChooseSpecialistUI extends UI implements ActionListener {
    JLabel descriptionTextField = new JLabel("Available times for the selected specialist:");
    JTextField specialistNumTextField = new JTextField("         ");
    JPanel panel = new JPanel();
    JList<String> appointmentData;
    JButton goBackHome = new JButton("Home");
    JTable appointmentsTable;
    JButton submitButton = new JButton("submit");
    private static ChooseSpecialistUI singleton = null;
    JCheckBox[][] checkboxes;
    ArrayList<ArrayList<String>> specialists;

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param frameTitle Title of the frame.
     */
    private ChooseSpecialistUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(500, 500));
        descriptionTextField.setPreferredSize(new Dimension(400, 40));
        panel.setLayout(new FlowLayout());
        panel.add(descriptionTextField);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        goBackHome.addActionListener(this);
        submitButton.addActionListener(this);
        frame.add(panel);
    }

    /**
     * Create or retrieve the singleton instance of ChooseSpecialistUI.
     *
     * @return The singleton instance of ChooseSpecialistUI.
     */
    public static ChooseSpecialistUI createUI() {
        if (singleton == null) {
            singleton = new ChooseSpecialistUI("Specialist");
        }
        return singleton;
    }

    /**
     * Handle the display of specialist information in a table.
     *
     * @param specialist The list of specialists to be displayed.
     */
    public void tableHandler(ArrayList<ArrayList<String>> specialist) {
        this.specialists = specialist;
        System.out.println(specialist);

        // Parse input to an array list of strings and show it in ChooseSpecialistUI
        String[][] specialistsList = new String[specialists.size()][3];
        int specialistCounter = 0;

        for (int i = 0; i < specialists.size(); i++) {
            String[] tempArray = new String[4];
            tempArray[0] = Integer.toString(i);
            String name = specialists.get(i).get(0);
            tempArray[1] = name;
            String profession = specialists.get(i).get(1);
            tempArray[2] = profession;

            specialistsList[specialistCounter] = tempArray;
            specialistCounter += 1;
        }

        String[] columnNames = {"ID", "Specialist Name", "Specialization"};
        DefaultTableModel model = new DefaultTableModel(specialistsList, columnNames);

        this.appointmentsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        panel.add(scrollPane);
        panel.add(specialistNumTextField);
        panel.add(submitButton);
        panel.add(goBackHome);
        frame.pack();
    }

    /**
     * Handle button click events.
     *
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.submitButton) {
            // Process the selection and request availability
            String numberString = specialistNumTextField.getText();
            numberString = numberString.replaceAll("\\s+", "");
            int number = Integer.parseInt(numberString);

            if (number < 0 || number >= specialists.size()) {
                showFailureNoOptions();
                return;
            }

            PortalGUI.returnSingleton().requestAvailability(specialists.get(number).get(2));
        }

        if (e.getSource() == this.goBackHome) {
            // Return to the home screen
            this.disposeFrame();
            HomeUI homeUIInstance = HomeUI.createUI();
            homeUIInstance.show();
        }
    }

    /**
     * Display a failure message for an invalid selection.
     */
    public void showFailureNoOptions() {
        JOptionPane.showMessageDialog(null, "Invalid option", "alert", JOptionPane.ERROR_MESSAGE);
    }
}
