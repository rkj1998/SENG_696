package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

    public class PastAppointmentsUI extends UI implements ActionListener  {
    JTextField descriptionTextField = new JTextField(20);
    JPanel panel = new JPanel();
    JList<String> appointmentData;
    JButton goBackHome = new JButton("Home");
    private static PastAppointmentsUI singleton = null;

    private PastAppointmentsUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        descriptionTextField.setPreferredSize(new Dimension(250, 40));
        descriptionTextField.setText("Click on specialist list to see the list of available specialists and make an appointmnt. " +
                "You can see the history of your past appointments by pressing the past appoints button. ");
        panel.setLayout(new FlowLayout());
        panel.add(descriptionTextField);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        goBackHome.addActionListener(this);
        frame.add(panel);
    }

        @Override
        public void show() {

        }

        public void show(String[] data)
    {
        frame.setVisible(true);

    }
    public static PastAppointmentsUI createUI()
    {
        if (singleton == null)
        {
            singleton = new PastAppointmentsUI("past");

        }
        return singleton;

    }
    public void appointmentDataHandler(String[] data)
    {
        appointmentData = new JList<>(data);
        frame.add(appointmentData);
        frame.pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.goBackHome)
        {
            HomeUI homeUIInstance = HomeUI.createUI();
            homeUIInstance.show();

        }

    }
}
