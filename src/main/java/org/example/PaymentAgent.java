package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * The PaymentAgent class represents an agent responsible for handling payment-related tasks in the healthcare appointment system.
 */
public class PaymentAgent extends Agent {

    /**
     * Setup method for initializing the PaymentAgent.
     */
    protected void setup() {
        System.out.println("Hello! Payment-agent " + getAID().getName() + " is ready.");

        // Register payment service so portal agent can search and find
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setName(getLocalName());
        sd.setType("payment");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        addBehaviour(new PaymentServer());
    }

    /**
     * Take down method for cleaning up resources when the agent is terminated.
     */
    protected void takeDown() {
        // Printout a dismissal message
        System.out.println("Payment-agent " + getAID().getName() + " terminating.");
    }

    /**
     * The paymentServer class is a CyclicBehaviour defining the behavior of the PaymentAgent.
     */
    private class PaymentServer extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(Utils.PAYMENT_REQUEST);
            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                System.out.println("PAYMENT: Payment request received.");
                // Process the payment request
                ACLMessage reply = msg.createReply();
                reply.setPerformative(Utils.PAYMENT_RESPONSE);
                reply.setContent(Utils.MESSAGE_SUCCESS);
                System.out.println("PAYMENT: Sending payment confirmation back to portal.");
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
