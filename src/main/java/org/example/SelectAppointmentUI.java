package org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The SelectAppointmentUI class represents the user interface for selecting an appointment time.
 */
public class SelectAppointmentUI extends UI implements ActionListener {
    JLabel descriptionTextField = new JLabel("");
    JTextField avalNumTextField = new JTextField("          ");
    JPanel panel = new JPanel();
    JTable appointmentsTable;
    JButton goBackHome = new JButton("Home");
    JButton submitButton = new JButton("Submit");
    private static SelectAppointmentUI singleton = null;
    JCheckBox[][] checkboxes;
    ArrayList<LocalDateTime> availabilities;

    /**
     * Private constructor to create a SelectAppointmentUI instance.
     *
     * @param frameTitle The title of the frame.
     */
    private SelectAppointmentUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(500, 500));
        descriptionTextField.setPreferredSize(new Dimension(400, 40));
        descriptionTextField.setText("Availability times for selected specialist");
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
     * Displays the SelectAppointmentUI frame.
     *
     * @param data An array of data (not used).
     */
    public void show(String[] data) {
        frame.setVisible(true);
    }

    /**
     * Creates a singleton instance of SelectAppointmentUI.
     *
     * @return The singleton instance of SelectAppointmentUI.
     */
    public static SelectAppointmentUI createUI() {
        if (singleton == null) {
            singleton = new SelectAppointmentUI("past");
        }
        return singleton;
    }

    /**
     * Handles the input availability times and displays them in a table.
     *
     * @param availabilities The list of available appointment times.
     */
    public void tableHandler(ArrayList<LocalDateTime> availabilities) {
        this.availabilities = availabilities;

        // Parse input to an array list of strings and show it in the ChooseSpecialistUI
        String[][] availabilityList = new String[availabilities.size()][2];
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        int availabilityCounter = 0;

        for (int i = 0; i < availabilities.size(); i++) {
            String[] tempArray = new String[2];
            tempArray[0] = Integer.toString(i);
            String time = availabilities.get(i).format(formatter);
            tempArray[1] = time;
            availabilityList[availabilityCounter] = tempArray;
            availabilityCounter += 1;
        }

        String[] columnNames = {"Number", "Date/Time",};
        DefaultTableModel model = new DefaultTableModel(availabilityList, columnNames);
        this.appointmentsTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(appointmentsTable);
        panel.add(scrollPane);
        panel.add(avalNumTextField);
        panel.add(submitButton);
        panel.add(goBackHome);
        frame.pack();
    }


    /**
     * Handles the action performed event, triggered when buttons are clicked.
     *
     * @param e The ActionEvent object.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.submitButton) {
            String numberString = avalNumTextField.getText();
            numberString = numberString.replaceAll("\\s+", "");
            int number = Integer.parseInt(numberString);

            if (number < 0 || number >= availabilities.size()) {
                showFailureNoOptions();
                return;
            }
            PortalGUI.returnSingleton().requestCreateAppointment(availabilities.get(number));
        }

        if (e.getSource() == this.goBackHome) {
            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.showHome();
        }
    }

    /**
     * Displays a failure message when no options are selected.
     */
    public void showFailureNoOptions() {
        JOptionPane.showMessageDialog(null, "No options selected", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Displays a failure message for a general appointment creation failure.
     */
    public void showFailureMessage() {
        JOptionPane.showMessageDialog(null, "Creating appointment failed", "Alert", JOptionPane.ERROR_MESSAGE);
    }
}
