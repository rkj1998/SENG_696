package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The PaymentUI class represents the user interface for handling payments in the healthcare appointment system.
 */
public class PaymentUI extends UI implements ActionListener {
    JLabel descriptionTextField = new JLabel();
    JButton submitButton = new JButton("Submit");
    private static PaymentUI singleton = null;
    JPanel panel = new JPanel();

    /**
     * Private constructor to enforce singleton pattern.
     *
     * @param frameTitle Title of the frame.
     */
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

    /**
     * Create or retrieve the singleton instance of PaymentUI.
     *
     * @return The singleton instance of PaymentUI.
     */
    public static PaymentUI createUI() {
        if (singleton == null) {
            singleton = new PaymentUI("Home");
        }
        return singleton;
    }

    /**
     * Display a failure message when the payment fails.
     */
    public void showFailureMessage() {
        JOptionPane.showMessageDialog(null, "Payment failed", "Alert", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Display the payment amount in the UI.
     *
     * @param amount The amount to be displayed.
     */
    public void showAmount(float amount) {
        descriptionTextField.setText("Price: " + amount);
    }

    /**
     * Handle button click events.
     *
     * @param e The ActionEvent.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // Close the payment UI and request payment from the portal
            this.disposeFrame();
            PortalGUI portal = PortalGUI.returnSingleton();
            portal.requestPayment();
        }
    }

    /**
     * Display a success message when the payment is successful.
     */
    public void showSuccess() {
        JOptionPane.showMessageDialog(null, "Payment successful!", "Success!", JOptionPane.INFORMATION_MESSAGE);
    }
}

