package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PaymentUI extends UI implements ActionListener  {
    JLabel descriptionTextField = new JLabel();
    JButton submitButton = new JButton("submit");
    private static PaymentUI singleton = null;
    JPanel panel = new JPanel();
    private PaymentUI(String frameTitle) {
        super(frameTitle);
        panel.setPreferredSize(new Dimension(250, 250));
        descriptionTextField.setPreferredSize(new Dimension(250, 40));
        submitButton.addActionListener(this);
        panel.setLayout(new FlowLayout());
        panel.add(descriptionTextField);
        panel.add(submitButton);
        frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 600));
        frame.add(panel);
        frame.pack();
    }


    public static PaymentUI createUI()
    {
        if (singleton == null)
        {
            singleton = new PaymentUI("Home");

        }
        return singleton;

    }

    public void showFailureMessage()
    {
        JOptionPane.showMessageDialog(null, "Payment failed", "alert", JOptionPane.ERROR_MESSAGE);

    }

    public void showAmount(float amount)
    {
        descriptionTextField.setText("price: " + amount);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton)
        {
            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestPayment();
        }


    }
    public void showSuccess()
    {
        JOptionPane.showMessageDialog(null, "Payment successful!", "Success!", JOptionPane.INFORMATION_MESSAGE);

    }
}
