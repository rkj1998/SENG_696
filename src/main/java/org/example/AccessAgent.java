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

    protected void setup(){

        patients = new Hashtable<String, User>();

        patients = Utils.generatePatients();

        // Printout a welcome message
        System.out.println("Hallo! Access-agent "+getAID().getName()+" is ready.");

        // Register access service so portal agent can search and find
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("access");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new userRegServer());

        addBehaviour(new userLoginServer());
    }

    public void updateUsers(User u){
        patients.put(u.getEmail(), u);
    }

    public boolean authUser(String username, String password){

        User u = patients.get(username);
        boolean authenticated = false;

        if (u != null)
            authenticated = u.getPassword().equals(password);
        return authenticated;
    }

    protected void takeDown() {
        System.out.println("Access-agent "+getAID().getName()+" terminating.");
    }

    private class userRegServer extends CyclicBehaviour{
        public void action(){
            MessageTemplate mt=MessageTemplate.MatchPerformative(Utils.REGISTER_REQUEST);
            ACLMessage msg=myAgent.receive(mt);
            if (msg!=null){
                String info = msg.getContent();
                String[] newInfo = info.split(Utils.DELIMITER);
                User newUser = new User(newInfo[0], newInfo[1], newInfo[2], newInfo[3]);

                System.out.println("ACCESS: Received a register request for user: " + newUser.getEmail());

                ACLMessage reply = msg.createReply();
                reply.setPerformative(Utils.REGISTER_RESPONSE);
                String content = "";

                if (patients.containsKey(newUser.getEmail())) {
                    content = Utils.MESSAGE_FAILURE;
                }
                else {
                    updateUsers(newUser);
                    content = Utils.MESSAGE_SUCCESS;
                }
                System.out.println("ACCESS: Sending registration confirmation back to portal");
                reply.setContent(content);
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }
    private class userLoginServer extends CyclicBehaviour{
        @Override
        public void action() {
            MessageTemplate mt=MessageTemplate.MatchPerformative(Utils.AUTH_REQUEST);
            ACLMessage msg=myAgent.receive(mt);
            if (msg!=null){
                String info = msg.getContent();
                String[] newInfo = info.split(Utils.DELIMITER);

                System.out.println("ACCESS: Received an authentication request for user: " + newInfo[0]);

                boolean flag = authUser(newInfo[0], newInfo[1]);
                ACLMessage reply = msg.createReply();
                reply.setPerformative(Utils.AUTH_RESPONSE);
                String content = "";
                if (flag){
                    String name = patients.get(newInfo[0]).getName();
                    content = Utils.MESSAGE_SUCCESS;
                    content = content.concat(Utils.DELIMITER);
                    content = content.concat(name);
                }
                else
                    content = Utils.MESSAGE_FAILURE;

                System.out.println("ACCESS: Sending authentication confirmation back to portal");
                reply.setContent(content);
                myAgent.send(reply);
            }
            else {
                block();
            }
        }
    }

}
