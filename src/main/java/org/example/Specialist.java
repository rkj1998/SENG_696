package org.example;

import java.util.ArrayList;

public class Specialist {
    //fieldsgi
    private String name;
    private String profession;
    private String email;
    private ArrayList<TimeSlot> schedule;
    public Specialist(String name, String profession, String email, ArrayList<TimeSlot> schedule) {
        this.name = name;
        this.profession = profession;
        this.email = email;
        this.schedule = schedule;
    }
    public void setSchedule(ArrayList<TimeSlot> schedule) {
        this.schedule = schedule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public ArrayList<TimeSlot> getSchedule() {
        return schedule;
    }

}
