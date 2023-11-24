package org.example;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Specialist {
    private String name;
    private String specialization;
    private String email;
    private ArrayList<TimeSlot> schedule;

    public Specialist(String name, String specialization, String email, ArrayList<TimeSlot> schedule) {
        this.name = name;
        this.specialization = specialization;
        this.email = email;
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<TimeSlot> getSchedule() {
        return schedule;
    }
}
