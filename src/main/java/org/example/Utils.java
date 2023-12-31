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
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Random;

/**
 * The Utils class provides utility methods for the application.
 */
public class Utils {
    // Constants for message performatives
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

    // Constants for success and failure messages
    public final static String MESSAGE_SUCCESS = "success";
    public final static String MESSAGE_FAILURE = "failure";

    // Delimiter for message content
    public final static String DELIMITER = "#";

    // Hourly wage for payment
    public final static Integer HOURLY_WAGE = 60; // In CAD for example

    public static final String SPECIALIST_NAME = "specialistName";
    public static final String SPECIALITY = "speciality";
    public static final String DATE_OF_APPOINTMENT = "dateOfAppointment";

    public static Hashtable<String, String> savedAppointments = new Hashtable<String, String>();


    /**
     * Generates a hashtable of specialists with hard-coded data.
     *
     * @return Hashtable<String, Specialist> containing specialists.
     */
    public static Hashtable<String, Specialist> generateSpecialists() {
        Hashtable<String, Specialist> specialists = new Hashtable<>();
        Random random = new Random();

        ArrayList<TimeSlot> timing = new ArrayList<>();
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 15, 10, 0), 50, false));
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 15, 11, 0), 50, false));
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 16, 14, 0), 50, false));
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 16, 16, 0), 50, false));
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 17, 18, 0), 50, false));
        timing.add(new TimeSlot(LocalDateTime.of(2022, Month.DECEMBER, 17, 19, 0), 50, false));

        String[] specializations = {"Neuroscience", "Cardiology", "Orthopedics", "Dermatology", "Ophthalmology"};

        HashSet<String> usedNames = new HashSet<>();

        for (int j = 0; j < 25; j++) {
            String specialistName;
            do {
                specialistName = generateHumanName(random);
            } while (usedNames.contains(specialistName));

            usedNames.add(specialistName);

            String email = specialistName.toLowerCase().replace(" ", ".") + "@example.com";
            String specialization = specializations[random.nextInt(specializations.length)];
            Specialist specialist = new Specialist(specialistName, specialization, email, timing);
            specialists.put(specialist.getEmail(), specialist);
        }


        return specialists;
    }

    /**
     * Generates a human name.
     *
     * @param random Random instance for generating random names.
     * @return String representing a generated human name.
     */
    private static String generateHumanName(Random random) {
        String[] firstNames = {"John", "Emma", "Michael", "Sophia", "David"};
        String[] lastNames = {"Smith", "Johnson", "Williams", "Jones", "Brown"};
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }

    /**
     * Generates a hashtable of patients with hard-coded data.
     *
     * @return Hashtable<String, User> containing patients.
     */
    public static Hashtable<String, User> generatePatients() {
        Hashtable<String, User> patients = new Hashtable<>();

        User userObj1 = new User("Mia", "123456", "Mia@gmail.com", "6471111111");
        User userObj2 = new User("Mike", "123456", "Mike@gmail.com", "6471111112");
        User userObj3 = new User("Peter", "123456", "Peter@gmail.com", "6471111113");
        User userObj4 = new User("Jack", "123456", "Jack@gmail.com", "6471111114");
        User userObj5 = new User("Sophi", "123456", "Sophi@gmail.com", "6471111115");

        patients.put(userObj1.getEmail(), userObj1);
        patients.put(userObj2.getEmail(), userObj2);
        patients.put(userObj3.getEmail(), userObj3);
        patients.put(userObj4.getEmail(), userObj4);
        patients.put(userObj5.getEmail(), userObj5);

        return patients;
    }

    /**
     * Searches for a service using a given agent and service name.
     *
     * @param myAgent      The agent initiating the search.
     * @param serviceName  The name of the service to search for.
     * @return AID representing the found service.
     */
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
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }
        return targetService;
    }
}
