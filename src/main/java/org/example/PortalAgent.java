package org.example;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class PortalAgent extends Agent{

    AID accessAgent;
    AID specialistAgent;
    AID appointmentAgent;
    AID paymentAgent;
    PortalGUI PortalUIInstance = PortalGUI.createUI(this);
    protected void setup() {

        System.out.println("Hello world! This is portal agent!");
        System.out.println("My local name is " + getAID().getLocalName());
        System.out.println("My GUID is " + getAID().getName());
        System.out.println("My addresses are " + String.join(",", getAID().getAddressesArray()));

        // a one shot behavior to get all services' AIDs
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                accessAgent = Utils.searchForService(myAgent, "access");
                specialistAgent = Utils.searchForService(myAgent, "specialist");
                appointmentAgent = Utils.searchForService(myAgent, "appointment");
                paymentAgent = Utils.searchForService(myAgent, "payment");
            }
        });

        PortalUIInstance.startGUI();
        addBehaviour(new CyclicBehaviour() {
            public void action() {

                ACLMessage msg;
                msg = myAgent.receive();

                if (msg != null) {
                    String[] payloadLst = msg.getContent().split(Utils.DELIMITER);
                    switch (msg.getPerformative()){
                        case Utils.REGISTER_RESPONSE:
                            System.out.println("PORTAL: Received register confirmation");
                            boolean registered = false;
                            if (payloadLst[0].equals(Utils.MESSAGE_SUCCESS)) {
                                registered = true;
                            } else if (payloadLst[0].equals(Utils.MESSAGE_FAILURE)) {
                                registered = false;
                            }
                            else
                                assert true : "UNKNOWN REGISTRATION MESSAGE";
                            PortalUIInstance.registerConfirm(registered);
                            break;

                        case Utils.AUTH_RESPONSE:
                            System.out.println("PORTAL: Received authentication confirmations");
                            String confirmation = payloadLst[0];
                            boolean authenticated = false;
                            String name = "";
                            if (confirmation.equals(Utils.MESSAGE_SUCCESS)) {
                                authenticated = true;
                                name = payloadLst[1];
                                System.out.println("PORTAL: Successfully authenticated user: " + name);
                            } else if (confirmation.equals(Utils.MESSAGE_FAILURE)) {
                                authenticated = false;
                                System.out.println("PORTAL: Failed to authenticate");
                            }
                            else
                                assert true : "UNKNOWN AUTH MESSAGE";
                            PortalUIInstance.loginConfirm(authenticated, name);
                            break;

                        case Utils.SPECIALISTS_LISTS_RESPONSE:
                            System.out.println("PORTAL: Received specialists list");
                            ArrayList<ArrayList<String>> specialistsLists = new ArrayList<>();
                            for (int i = 0; i < payloadLst.length; i++) {
                                if (i % 3 == 0 && i != 0) {
                                    ArrayList<String> specialistInfo = new ArrayList<>();
                                    specialistInfo.add(payloadLst[i-3]);
                                    specialistInfo.add(payloadLst[i-2]);
                                    specialistInfo.add(payloadLst[i-1]);
                                    specialistsLists.add(specialistInfo);
                                }
                            }

                            PortalUIInstance.showSpecialistList(specialistsLists);
                            break;

                        case Utils.AVAILABILITY_RESPONSE:
                            System.out.println("PORTAL: Received specialist's availability");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            ArrayList<LocalDateTime> dateTimes = new ArrayList<>();

                            for (String dateTimeStr: payloadLst) {
                                dateTimes.add(LocalDateTime.parse(dateTimeStr, formatter));
                            }
                            PortalUIInstance.showAvailability(dateTimes);
                            break;

                        case Utils.PAYMENT_RESPONSE:
                            System.out.println("PORTAL: Received payment confirmation");
                            boolean paid = payloadLst[0].equals(Utils.MESSAGE_SUCCESS);
                            PortalUIInstance.paymentConfirm(paid);
                            break;

                        case Utils.CREATE_APPOINTMENT_RESPONSE:
                            System.out.println("PORTAL: Received appointment creation's confirmation");
                            boolean appointmentCreated = false;
                            int appointmentID = -1;
                            float amount = -1;
                            if (payloadLst[0].equals(Utils.MESSAGE_SUCCESS)) {
                                appointmentCreated = true;
                                appointmentID = Integer.parseInt(payloadLst[1]);
                                amount = Float.parseFloat(payloadLst[2]);
                            }
                            PortalUIInstance.appointmentConfirm(appointmentCreated, appointmentID, amount);
                            break;
                    }
                }
            }
        });

    }

    protected void takeDown() {
        System.out.println("Portal agent "+getAID().getName()+" terminating.");
    }

    public void registerRequest(String name, String email, String phoneNumber, String password) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.REGISTER_REQUEST);
                List<String> payloadLst = Arrays.asList(name, password, email, phoneNumber);

                String payload = String.join(Utils.DELIMITER, payloadLst);

                System.out.println("PORTAL: Requesting to register " + email + " to " + accessAgent.getLocalName());

                message.setContent(payload);
                message.addReceiver(accessAgent);
                send(message);
            }
        });
    }

    public void authRequest(String email, String passwordHash) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.AUTH_REQUEST);
                List<String> payloadLst = Arrays.asList(email, passwordHash);

                String payload = String.join(Utils.DELIMITER, payloadLst);

                System.out.println("PORTAL: Requesting to authenticate " + email + " to " + accessAgent.getLocalName());

                message.setContent(payload);
                message.addReceiver(accessAgent);
                send(message);
            }
        });
    }

    public void specialistsListRequest() {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.SPECIALISTS_LISTS_REQUEST);

                System.out.println("PORTAL: Requesting to get specialists list from " + specialistAgent.getLocalName());

                message.addReceiver(specialistAgent);
                send(message);
            }
        });
    }

    public void availabilityRequest(String specialistEmail) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.AVAILABILITY_REQUEST);

                System.out.println("PORTAL: Requesting to get availability of specialist "
                        + specialistEmail + " from " + specialistAgent.getLocalName());

                message.setContent(specialistEmail);
                message.addReceiver(specialistAgent);
                send(message);
            }
        });
    }

    public void paymentRequest(Integer appointmentID) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.PAYMENT_REQUEST);

                String payload = appointmentID.toString();

                System.out.println("PORTAL: Requesting to pay " + Utils.HOURLY_WAGE + "$ to " + paymentAgent.getLocalName());

                message.setContent(payload);
                message.addReceiver(paymentAgent);
                send(message);
            }
        });
    }
    public void createAppointmentRequest(LocalDateTime appDateTime, String patientEmail, String specialistEmail) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                ACLMessage message = new ACLMessage(Utils.CREATE_APPOINTMENT_REQUEST);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String dateTimeStr = appDateTime.format(formatter);
                List<String> payloadLst = Arrays.asList(
                        dateTimeStr,
                        patientEmail,
                        specialistEmail);

                String payload = String.join(Utils.DELIMITER, payloadLst);

                System.out.println("PORTAL: Requesting to make an appointment on  " + appDateTime.toString()
                        + " to " + appointmentAgent.getLocalName());

                message.setContent(payload);
                message.addReceiver(appointmentAgent);
                send(message);
            }
        });
    }
}

