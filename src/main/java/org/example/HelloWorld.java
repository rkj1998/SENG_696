package org.example;

import jade.core.Agent;
public class HelloWorld extends Agent{
    protected void setup(){
        System.out.println("Hello world! I'm an agent!");
        System.out.println("My local name is"+getAID().getName());
        System.out.println("My id is"+getAID().getName());
        System.out.println("My addresses are"+String.join(",",getAID().getAddressesArray()));
    }
}
