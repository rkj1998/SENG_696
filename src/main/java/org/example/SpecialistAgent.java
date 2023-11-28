package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Set;

public class SpecialistAgent extends Agent{
    Hashtable <String, Specialist> specialists;


    protected void setup() {

        // Register specialist service so portal agent can search and find
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("specialist");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // fill the specialists list with some hard-coded data
        specialists = Utils.generateSpecialists();

        addBehaviour(new CyclicBehaviour() {
            public void action() {

                ACLMessage msg;
                msg = myAgent.receive();
                if (msg != null) {
                    String content = "";
                    switch (msg.getPerformative()){
                        case Utils.SPECIALISTS_LISTS_REQUEST:
                            System.out.println("SPECIALIST: specialists' list request received");
                            String requestedSpecialization = msg.getContent();

                            ACLMessage replySpecialistsList = msg.createReply();
                            replySpecialistsList.setPerformative(Utils.SPECIALISTS_LISTS_RESPONSE);

                            for (Specialist specialist : specialists.values()) {
                                if (specialist.getSpecialization().equals(requestedSpecialization)) {
                                    content = content.concat(specialist.getName());
                                    content = content.concat(Utils.DELIMITER);
                                    content = content.concat(specialist.getSpecialization());
                                    content = content.concat(Utils.DELIMITER);
                                    content = content.concat(specialist.getEmail());
                                    content = content.concat(Utils.DELIMITER);
                                }
                            }

                            System.out.println("SPECIALIST: Sending specialists list back to portal");
                            replySpecialistsList.setContent(content);
                            send(replySpecialistsList);
                            break;



                        case Utils.AVAILABILITY_REQUEST:
                            System.out.println("SPECIALIST: chosen specialist's availability request received");
                            //get a message from the portal and parse the message and iterate over their times
                            String[] payloadLst = msg.getContent().split(Utils.DELIMITER);
                            String selectedSpecialistEmail = payloadLst[0];
                            Specialist selectedSpecialist = specialists.get(selectedSpecialistEmail);

                            ACLMessage replyAvailability = msg.createReply();
                            replyAvailability.setPerformative(Utils.AVAILABILITY_RESPONSE);
                            if (selectedSpecialist != null) {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                                for (int i = 0; i < selectedSpecialist.getSchedule().size(); i++){
                                    if (!selectedSpecialist.getSchedule().get(i).getReserved()) {
                                        content = content.concat(selectedSpecialist.getSchedule().get(i).getStartingDateTime().format(formatter));
                                        content = content.concat(Utils.DELIMITER);
                                    }
                                }
                            }
                            else {
                                content = Utils.MESSAGE_FAILURE;
                            }

                            System.out.println("SPECIALIST: Sending selected specialist's availability back to portal");

                            replyAvailability.setContent(content);
                            send(replyAvailability);
                            break;
                    }
                }
            }
        });

    }

}

