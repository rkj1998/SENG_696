package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUI extends UI implements ActionListener  {
    JLabel welcomeTextLabel = new JLabel();
    JButton pastAPPButton = new JButton("Past Appointments");
    JButton specialistButton = new JButton("Specialists List");
    private static HomeUI singleton = null;
    JPanel panel = new JPanel();

    private HomeUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        pastAPPButton.addActionListener(this);
        specialistButton.addActionListener(this);

        panel.setLayout(new FlowLayout());
        panel.add(welcomeTextLabel);
        panel.add(specialistButton);

        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.add(panel);
        frame.pack();
    }


    public static HomeUI createUI()
    {
        if (singleton == null)
        {
            singleton = new HomeUI("Home");

        }
        return singleton;

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pastAPPButton)
        {
            this.disposeFrame();
            //TODO:ask for past appointment history
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestPastAppointments("Temp name"); //TDDO: change temp name to user name!
        }
        else if (e.getSource() == specialistButton)
        {
            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestSpecialistList();
        }

    }
    public void setName(String name)
    {
        welcomeTextLabel.setText("Welcome user " + name);
    }
}
