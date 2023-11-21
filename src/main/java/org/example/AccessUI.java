package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccessUI extends UI implements ActionListener {

    JLabel descriptionTextField = new JLabel("Please login or register.");

    JButton button1 = new JButton("Login");
    JButton button2 = new JButton("Register");
    JPanel panel1 = new JPanel();

    JPanel panel2 = new JPanel();

    private static AccessUI singleton = null;


    private AccessUI(String frameTitle) {
        super(frameTitle);
        panel1.setPreferredSize(new Dimension(250, 250));
        panel2.setPreferredSize(new Dimension(250, 250));
        button1.addActionListener(this);
        button2.addActionListener(this);
        this.panel1.add(descriptionTextField);
        this.panel1.add(button1);
        this.panel1.add(button2);

        this.frame.add(panel1);
        this.frame.add(panel2);
        this.frame.pack();
    }
    public static AccessUI createUI()
    {
        if (singleton == null)
        {
            singleton = new AccessUI("Specialist Platform");

        }
        return singleton;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.button1)
        {
            //open window for login!
            this.disposeFrame();
            LoginUI myLoginWindow = LoginUI.createUI();
            myLoginWindow.show();
        }
        else if(e.getSource() == this.button2)
        {
            //open window for register!
            this.disposeFrame();
            RegisterUI myLoginWindow = RegisterUI.createUI();
            myLoginWindow.show();
        }

    }




}
