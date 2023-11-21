package org.example;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {
        // Get the JADE runtime
        Runtime rt = Runtime.instance();

        // Create a profile
        Profile profile = new ProfileImpl();

        // Create the main container
        AgentContainer container = rt.createMainContainer(profile);

        // Initialize and start the HelloWorld agent
        try {
            Object[] agentArgs = new Object[0]; // You can pass arguments to your agent here
            AgentController agentController = container.createNewAgent("hello", "org.example.HelloWorld", agentArgs);
            agentController.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
