package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.Hashtable;

public class AccessAgent extends Agent {
    private Hashtable<String, User> patients;

    protected void setup() {
        // Initialize the hashtable of patients with generated data
        patients = Utils.generatePatients();

        // Print a welcome message
        System.out.println("Hallo! Access-agent " + getAID().getName() + " is ready.");

        // Register access service so portal agent can search and find
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("access");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Add behaviors for user registration and user login
        addBehaviour(new UserRegServer());
        addBehaviour(new UserLoginServer());
    }

    // Method to update the users in the hashtable
    public void updateUsers(User u) {
        patients.put(u.getEmail(), u);
    }

    // Method to authenticate a user based on username and password
    public boolean authUser(String username, String password) {
        User u = patients.get(username);
        boolean authenticated = false;

        if (u != null)
            authenticated = u.getPassword().equals(password);

        return authenticated;
    }

    // Agent termination method
    protected void takeDown() {
        System.out.println("Access-agent " + getAID().getName() + " terminating.");
    }

    // Inner class for handling user registration requests
    private class UserRegServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(Utils.REGISTER_REQUEST);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Extract information from the received message
                String info = msg.getContent();
                String[] newInfo = info.split(Utils.DELIMITER);
                User newUser = new User(newInfo[0], newInfo[1], newInfo[2], newInfo[3]);

                System.out.println("ACCESS: Received a register request for user: " + newUser.getEmail());

                // Prepare a reply message
                ACLMessage reply = msg.createReply();
                reply.setPerformative(Utils.REGISTER_RESPONSE);
                String content = "";

                // Check if the user already exists
                if (patients.containsKey(newUser.getEmail())) {
                    content = Utils.MESSAGE_FAILURE;
                } else {
                    // Update the users and send a success message
                    updateUsers(newUser);
                    content = Utils.MESSAGE_SUCCESS;
                }

                System.out.println("ACCESS: Sending registration confirmation back to portal");
                reply.setContent(content);
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    // Inner class for handling user login requests
    private class UserLoginServer extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(Utils.AUTH_REQUEST);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // Extract information from the received message
                String info = msg.getContent();
                String[] newInfo = info.split(Utils.DELIMITER);

                System.out.println("ACCESS: Received an authentication request for user: " + newInfo[0]);

                // Authenticate the user and prepare a reply message
                boolean flag = authUser(newInfo[0], newInfo[1]);
                ACLMessage reply = msg.createReply();
                reply.setPerformative(Utils.AUTH_RESPONSE);
                String content = "";

                if (flag) {
                    // If authentication is successful, include user name in the success message
                    String name = patients.get(newInfo[0]).getName();
                    content = Utils.MESSAGE_SUCCESS + Utils.DELIMITER + name;
                } else {
                    content = Utils.MESSAGE_FAILURE;
                }

                System.out.println("ACCESS: Sending authentication confirmation back to portal");
                reply.setContent(content);
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
