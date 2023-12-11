package org.example;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

/**
 * The PortalGUI class represents the user interface for the healthcare appointment system.
 */
public class PortalGUI {
    private static PortalGUI singleton = null;
    PortalAgent portalAgentInstance;
    String patientEmail;
    String specialistEmail;
    String patientName;
    Integer appID;
    private PortalGUI(PortalAgent portalAgentInstance) {
        this.portalAgentInstance = portalAgentInstance;
    }

    /**
     * Creates a singleton instance of PortalGUI.
     *
     * @param portalAgentInstance The associated PortalAgent instance.
     * @return The singleton instance of PortalGUI.
     */
    public static PortalGUI createUI(PortalAgent portalAgentInstance) {
        if (singleton == null) {
            singleton = new PortalGUI(portalAgentInstance);
        }
        return singleton;
    }

    /**
     * Retrieves the singleton instance of PortalGUI.
     *
     * @return The singleton instance of PortalGUI.
     */
    public static PortalGUI returnSingleton() {
        return singleton;
    }

    /**
     * Starts the access UI.
     */
    public void startGUI() {
        AccessUI accessUIInstance = AccessUI.createUI();
        accessUIInstance.show();
    }

    /**
     * Displays the home UI.
     */
    public void showHome() {
        System.out.println(Utils.savedAppointments);
        HomeUI home = HomeUI.createUI();
        home.setName(this.patientName);
        home.show();
    }

    /**
     * Requests a list of specialists based on the provided specialization.
     *
     * @param specialization The specialization for filtering specialists (optional).
     */
    public void requestSpecialistList(String specialization) {
        if (specialization.isEmpty()) {
            // Return all specialists
            portalAgentInstance.specialistsListRequest("");
        } else {
            // Return specialists for the selected specialization
            portalAgentInstance.specialistsListRequest(specialization);
        }
    }

    /**
     * Requests the availability of a specialist.
     *
     * @param specialistEmail Email of the specialist.
     */
    public void requestAvailability(String specialistEmail) {
        portalAgentInstance.availabilityRequest(specialistEmail);
    }

    /**
     * Requests user registration.
     *
     * @param name     User's name.
     * @param email    User's email.
     * @param phone    User's phone number.
     * @param password User's password.
     */
    public void requestRegister(String name, String email, String phone, String password) {
        portalAgentInstance.registerRequest(name, email, phone, password);
    }

    /**
     * Requests a payment for a specific appointment.
     */
    public void requestPayment() {
        portalAgentInstance.paymentRequest(appID);
    }

    /**
     * Requests the creation of a new appointment.
     *
     * @param appDateTime      Date and time of the appointment.
     */
    public void requestCreateAppointment(LocalDateTime appDateTime) {
        portalAgentInstance.createAppointmentRequest(appDateTime, this.patientEmail, this.specialistEmail);
    }


    public void requestPastAppointments() {
        // Assuming Utils.savedAppointments is the Hashtable containing saved appointments
        Hashtable<String, String> savedAppointments = Utils.savedAppointments;

        if (!savedAppointments.isEmpty()) {
            // Create an array to hold the data for PastAppointmentsUI
            String[] pastAppointmentsData = new String[savedAppointments.size()];

            int index = 0;

            for (String name : savedAppointments.keySet()) {
                // Create a formatted string for each user's past appointments
                String userData = "Specialist : " + extractNamesFromEmail(name) + " - Appointments: " + savedAppointments.get(name);
                pastAppointmentsData[index] = userData;
                index++;
            }

            // Create or retrieve the singleton instance of PastAppointmentsUI
            PastAppointmentsUI pastAppointmentsUI = PastAppointmentsUI.createUI();
            // Call the appointmentDataHandler method to display past appointments
            pastAppointmentsUI.appointmentDataHandler(pastAppointmentsData);
            // Show the PastAppointmentsUI frame
            pastAppointmentsUI.show();
        } else {
            // Display a message if there are no past appointments
            JOptionPane.showMessageDialog(null, "No past appointments available.", "Information", JOptionPane.INFORMATION_MESSAGE);

            // Go back to the home screen
            HomeUI homeUIInstance = HomeUI.createUI();
            homeUIInstance.show();
        }
    }



    public static String extractNamesFromEmail(String email) {
        // Check if the email is not null and contains a dot
        String name = "";
        for (int i=0;i<email.length();i++){
            if(email.charAt(i)!='.'&&email.charAt(i)!='@'){
                name+=email.charAt(i);
            }
            else if (email.charAt(i)=='.'){
                name+=" ";
            }
            else{
                break;
            }
        }

        // Return null if the email format is not as expected
        return name;
    }


    /**
     * Requests user login.
     *
     * @param email    User's email.
     * @param password User's password.
     */
    public void requestLoginUser(String email, String password) {
        this.patientEmail = email;
        portalAgentInstance.authRequest(email, password);
    }

    /**
     * Displays the list of specialists in the home UI.
     *
     * @param specialists List of specialists' information.
     */
    public void showSpecialistList(ArrayList<ArrayList<String>> specialists) {
        HomeUI home = HomeUI.createUI();
        home.disposeFrame();
        ChooseSpecialistUI specialistUI = ChooseSpecialistUI.createUI();
        specialistUI.tableHandler(specialists);
        specialistUI.show();
    }

    /**
     * Displays the availability of a specialist in the choose specialist UI.
     *
     * @param availabilityTimes List of available date and time slots.
     */
    public void showAvailability(ArrayList<LocalDateTime> availabilityTimes) {
        ChooseSpecialistUI specialistUI = ChooseSpecialistUI.createUI();
        specialistUI.disposeFrame();
        SelectAppointmentUI appUI = SelectAppointmentUI.createUI();
        appUI.tableHandler(availabilityTimes);
        appUI.show();
    }

    /**
     * Handles the login confirmation response.
     *
     * @param loginConfirm Whether the login was successful.
     * @param name         User's name.
     */
    public void loginConfirm(boolean loginConfirm, String name) {
        this.patientName = name;
        LoginUI loginUIInstance = LoginUI.createUI();
        if (loginConfirm) {
            loginUIInstance.showSuccessLogin();
            loginUIInstance.disposeFrame();
            showHome();
        } else {
            loginUIInstance.showFailureMessage();
        }
    }

    /**
     * Handles the appointment confirmation response.
     *
     * @param appConfirm Whether the appointment was successfully created.
     * @param appID      Appointment ID.
     * @param amount     Payment

    amount.
     */
    public void appointmentConfirm(boolean appConfirm, Integer appID, float amount) {
        this.appID = appID;
        SelectAppointmentUI selectAPPUI = SelectAppointmentUI.createUI();
        if (appConfirm) {
            selectAPPUI.disposeFrame();
            PaymentUI paymentUIInstance = PaymentUI.createUI();
            paymentUIInstance.showAmount(amount);
            paymentUIInstance.show();
        } else {
            selectAPPUI.showFailureMessage();
        }
    }

    /**
     * Handles the payment confirmation response.
     *
     * @param confirm Whether the payment was successful.
     */
    public void paymentConfirm(boolean confirm) {
        PaymentUI paymentUIInstance = PaymentUI.createUI();
        if (confirm) {
            paymentUIInstance.showSuccess();
            paymentUIInstance.disposeFrame();
            showHome();
        } else {
            paymentUIInstance.showFailureMessage();
        }
    }

    /**
     * Handles the registration confirmation response.
     *
     * @param confirm Whether the registration was successful.
     */
    public void registerConfirm(boolean confirm) {
        RegisterUI registerUIInstance = RegisterUI.createUI();
        if (confirm) {
            registerUIInstance.showSuccessRegister();
            registerUIInstance.disposeFrame();
            LoginUI loginUIInstance = LoginUI.createUI();
            loginUIInstance.show();
        } else {
            registerUIInstance.showFailureMessage();
        }
    }
}
