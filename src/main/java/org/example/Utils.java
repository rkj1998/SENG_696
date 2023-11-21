package org.example;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Hashtable;

public class Utils {
    public final static int FAILURE = 0;
    public final static int DONE = 1;
    public final static int REGISTER_REQUEST = 2;
    public final static int REGISTER_RESPONSE = 3;
    public final static int AUTH_REQUEST = 4;
    public final static int AUTH_RESPONSE = 5;
    public final static int SPECIALISTS_LISTS_REQUEST = 6;
    public final static int SPECIALISTS_LISTS_RESPONSE = 7;
    public final static int AVAILABILITY_REQUEST = 8;
    public final static int AVAILABILITY_RESPONSE = 9;
    public final static int PAYMENT_REQUEST = 10;
    public final static int PAYMENT_RESPONSE = 11;
    public final static int CREATE_APPOINTMENT_REQUEST = 12;
    public final static int CREATE_APPOINTMENT_RESPONSE = 13;

    public final static String MESSAGE_SUCCESS = "success";
    public final static String MESSAGE_FAILURE = "failure";

    public final static String DELIMITER = "#";

    public final static Integer HOURLY_WAGE = 60; // In CAD for example

    public static Hashtable<String, Specialist> generateSpecialists() {
        // a sample specialist list is hard coded and returned in this func
        Hashtable<String, Specialist> specialists = new Hashtable<String, Specialist>();

        TimeSlot timeSlot1 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 0),
                50, false);
        TimeSlot timeSlot2 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 15, 11, 0),
                50, false);
        TimeSlot timeSlot3 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 16, 14, 0),
                50, false);
        TimeSlot timeSlot4 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 16, 16, 0),
                50, false);
        TimeSlot timeSlot5 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 17, 18, 0),
                50, false);
        TimeSlot timeSlot6 = new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 17, 19, 0),
                50, false);

        ArrayList<TimeSlot> timing = new ArrayList<TimeSlot>();
        timing.add(timeSlot1);
        timing.add(timeSlot2);
        timing.add(timeSlot3);
        timing.add(timeSlot4);
        timing.add(timeSlot5);
        timing.add(timeSlot6);

        Specialist specialist1= new Specialist("Mona Falsafi", "neurobiology1",
                "mona.falsafi@yahoo.com", timing);
        Specialist specialist2= new Specialist("Nazanin Habibi", "neurobiology2",
                "nazanin.habibi@yahoo.com", timing);
        Specialist specialist3= new Specialist("Shadi Fatourehchi", "neurobiology3",
                "shadi.fatourehchi@yahoo.com",  timing);
        Specialist specialist4= new Specialist("Khaled Aslani", "neurobiology4",
                "khaled.aslani@yahoo.com", timing);
        Specialist specialist5= new Specialist("Marzie Masoudi", "neurobiology5"
                ,"marzie.masoudi@yahoo.com",  timing);
        Specialist specialist6= new Specialist("Niousha Pordavoody", "neurobiology6"
                ,"niousha.pordavoody@yahoo.com", timing);
        Specialist specialist7= new Specialist("Maryam Falahatpishe", "neurobiology7",
                "maryam.falahatpishe@yahoo.com", timing);
        Specialist specialist8= new Specialist("Mohamad Mehdi Badiei", "neurobiology8"
                ,"mohammad.badiei@yahoo.com", timing);
        Specialist specialist9= new Specialist("Amir Fadaeipour", "neurobiology9",
                "amir.fadaeipour@yahoo.com", timing);
        Specialist specialist10= new Specialist("Azar Saeidi nasab", "neurobiology10",
                "azar.saeidi@yahoo.com", timing);

        specialists.put(specialist1.getEmail(),specialist1);
        specialists.put(specialist2.getEmail(), specialist2);
        specialists.put(specialist3.getEmail(), specialist3);
        specialists.put(specialist4.getEmail(),specialist4);
        specialists.put(specialist5.getEmail(),specialist5);
        specialists.put(specialist6.getEmail(),specialist6);
        specialists.put(specialist7.getEmail(),specialist7);
        specialists.put(specialist8.getEmail(),specialist8);
        specialists.put(specialist9.getEmail(),specialist9);
        specialists.put(specialist10.getEmail(),specialist10);

        return specialists;
    }

    public static Hashtable<String, User> generatePatients() {

        Hashtable<String, User> patients = new Hashtable<>();

        User userObj1 = new User("Mia","123456","Mia@gmail.com","6471111111");
        User userObj2 = new User("Mike","123456","Mike@gmail.com","6471111112");
        User userObj3 = new User("Peter","123456","Peter@gmail.com","6471111113");
        User userObj4 = new User("Jack","123456","Jack@gmail.com","6471111114");
        User userObj5 = new User("Sophi","123456","Sophi@gmail.com","6471111115");

        patients.put(userObj1.getEmail(),userObj1);
        patients.put(userObj2.getEmail(),userObj2);
        patients.put(userObj3.getEmail(),userObj3);
        patients.put(userObj4.getEmail(),userObj4);
        patients.put(userObj5.getEmail(),userObj5);

        return patients;
    }

    public static AID searchForService(Agent myAgent, String serviceName) {
        AID targetService = new AID();
        DFAgentDescription template = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(serviceName);
        template.addServices(sd);
        try {
            DFAgentDescription[] result = DFService.search(myAgent, template);
            System.out.println("Found the following agent(s):");
            for (DFAgentDescription dfAgentDescription : result) {
                targetService = dfAgentDescription.getName();
                System.out.println(targetService.getName());
            }
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return targetService;
    }
}
