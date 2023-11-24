package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PortalGUI {
    private static PortalGUI singleton = null;
    PortalAgent portalAgentInstance;
    String patientEmail;
    String specialistEmail;
    String patientName;
    Integer appID;
    private PortalGUI(PortalAgent portalAgentInstance)
    {
        this.portalAgentInstance = portalAgentInstance;
    }


    public static PortalGUI createUI(PortalAgent portalAgentInstance)
    {
        if (singleton == null)
        {
            singleton = new PortalGUI(portalAgentInstance);

        }
        return singleton;

    }
    public static PortalGUI returnSingleton()
    {
        return singleton;
    }


    public void startGUI()
    {
        AccessUI accessUIInstance = AccessUI.createUI();
        accessUIInstance.show();


    }

    public void showHome()
    {
        HomeUI home = HomeUI.createUI();
        home.setName(this.patientName);
        home.show();

    }

    public void requestSpecialistList()
    {
        portalAgentInstance.specialistsListRequest();
    }

    public void requestSpecialist()
    {

        portalAgentInstance.specialistsListRequest();
    }

    public void requestAvailability(String specialistEmail)
    {
        portalAgentInstance.availabilityRequest(specialistEmail);
    }
    public void requestRegister(String name,String email,String phone,String password)
    {
        portalAgentInstance.registerRequest(name,email,phone,password);
    }
    public void requestPayment()
    {
        portalAgentInstance.paymentRequest(appID);
    }
    public void requestCreateAppointment(LocalDateTime appDateTime)
    {
        portalAgentInstance.createAppointmentRequest(appDateTime, this.patientEmail, this.specialistEmail);
    }
    public void requestPastAppointments(String name)
    {
        //TODO: call the request function (not implemented yet.)
    }
    public void requestLoginUser(String email, String password)
    {
        this.patientEmail = email;
        portalAgentInstance.authRequest(email, password);
    }
    

    public void showSpecialistList(ArrayList<ArrayList<String>> specialists)
    {
        HomeUI home = HomeUI.createUI();
        home.disposeFrame();
        ChooseSpecialistUI specialistUI = ChooseSpecialistUI.createUI();
        specialistUI.tableHandler(specialists);
        specialistUI.show();
    }
    public void showAvailability(ArrayList<LocalDateTime> availabilityTimes)
    {
        ChooseSpecialistUI specialistUI = ChooseSpecialistUI.createUI();
        specialistUI.disposeFrame();
        //parse input to an array list of strings and show it in chooseSpecialistUI
        SelectAppointmentUI appUI = SelectAppointmentUI.createUI();
        appUI.tableHandler(availabilityTimes);
        appUI.show();
    }

    public void loginConfirm(boolean loginConfirm, String name)
    {
        this.patientName = name;
        if (loginConfirm)
        {
            LoginUI loginUIInstance = LoginUI.createUI();
            loginUIInstance.showSuccessLogin();
            loginUIInstance.disposeFrame();
            showHome();
        }
        else {
            LoginUI loginUIInstance = LoginUI.createUI();
            loginUIInstance.showFailureMessage();
        }
    }

    public void appointmentConfirm(boolean appConfirm, Integer appID, float amount)
    {
        this.appID = appID;
        if (appConfirm)
        {
            SelectAppointmentUI selectAPPUI = SelectAppointmentUI.createUI();
            selectAPPUI.disposeFrame();
            PaymentUI paymentUIInstance = PaymentUI.createUI();
            paymentUIInstance.showAmount(amount);
            paymentUIInstance.show();
        }
        else {
            SelectAppointmentUI selectAPPUI = SelectAppointmentUI.createUI();
            selectAPPUI.showFailureMessage();
        }

    }


    public void paymentConfirm(boolean confirm)
    {
        if (confirm)
        {
            PaymentUI paymentUIInstance = PaymentUI.createUI();
            paymentUIInstance.showSuccess();
            paymentUIInstance.disposeFrame();
            showHome();
        }
        else {
            PaymentUI paymentUIInstance = PaymentUI.createUI();
            paymentUIInstance.showFailureMessage();
        }
    }


    public void registerConfirm(boolean confirm)
    {
        if (confirm)
        {
            RegisterUI registerUIInstance = RegisterUI.createUI();
            registerUIInstance.showSuccessRegister();
            registerUIInstance.disposeFrame();
            LoginUI loginUIInstance = LoginUI.createUI();
            loginUIInstance.show();

        }
        else {
            RegisterUI registerUIInstance = RegisterUI.createUI();
            registerUIInstance.showFailureMessage();
        }
    }




}
