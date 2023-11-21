package org.example;

import java.time.LocalDateTime;

public class Appointment {
    private Integer appointmentID;
    private String patientEmail;
    private String specialistEmail;
    private LocalDateTime dateTime;
    private Integer amount; // price
    private Boolean paid;
    private Integer paymentTrackNo;
    private LocalDateTime paymentDateTime;

    public Appointment(Integer appointmentID, String patientEmail, String specialistEmail, LocalDateTime dateTime,
                       Integer amount, Boolean paid) {
        this.appointmentID = appointmentID;
        this.patientEmail = patientEmail;
        this.specialistEmail = specialistEmail;
        this.dateTime = dateTime;
        this.amount = amount;
        this.paid = paid;
    }

    public Integer getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Integer appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getPatientEmail() {
        return patientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.patientEmail = patientEmail;
    }

    public String getSpecialistEmail() {
        return specialistEmail;
    }

    public void setSpecialistEmail(String specialistEmail) {
        this.specialistEmail = specialistEmail;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Integer getPaymentTrackNo() {
        return paymentTrackNo;
    }

    public void setPaymentTrackNo(Integer paymentTrackNo) {
        this.paymentTrackNo = paymentTrackNo;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }
}
