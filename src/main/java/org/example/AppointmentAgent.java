package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class AppointmentAgent extends Agent{

    ArrayList<Appointment> appointments;

    protected void setup() {
        appointments = new ArrayList<Appointment>();
        // Register appointment service so portal agent can search and find
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("appointment");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                ACLMessage msg;
                msg = myAgent.receive();

                if (msg != null) {
                    String[] payloadLst = msg.getContent().split(Utils.DELIMITER);
                    switch (msg.getPerformative()){
                        case Utils.CREATE_APPOINTMENT_REQUEST:
                            ACLMessage reply = msg.createReply();
                            reply.setPerformative(Utils.CREATE_APPOINTMENT_RESPONSE);
                            String content = "";

                            String[] contentLst = msg.getContent().split(Utils.DELIMITER);
                            String dateTimeStr = contentLst[0];
                            String patientEmail = contentLst[1];
                            String specialistEmail = contentLst[2];

                            System.out.println("APPOINTMENT: appointment creation received for " + patientEmail +
                                    " on " + dateTimeStr);

                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, formatter);

                            boolean alreadyExists = false;
                            for (Appointment appointment : appointments) {
                                if (appointment.getPatientEmail().equals(patientEmail) &&
                                        appointment.getSpecialistEmail().equals(specialistEmail)) {
                                    alreadyExists = true;
                                    break;
                                }
                            }

                            if (alreadyExists){
                                content = Utils.MESSAGE_FAILURE;
                            }
                            else {
                                Integer newAppointmentID = appointments.size();
                                Appointment newAppointment = new Appointment(newAppointmentID, patientEmail,
                                        specialistEmail, dateTime, Utils.HOURLY_WAGE, Boolean.FALSE);
                                appointments.add(newAppointment);
                                content = Utils.MESSAGE_SUCCESS;
                                content = content.concat(Utils.DELIMITER);
                                content = content.concat(newAppointmentID.toString());
                                content = content.concat(Utils.DELIMITER);
                                content = content.concat(Utils.HOURLY_WAGE.toString());
                            }

                            reply.setContent(content);
                            send(reply);
                            break;

                        default:
                            break;
                    }
                }
            }
        });
    }
}
