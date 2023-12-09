package org.example;

import java.time.LocalDateTime;

/**
 * The Appointment class represents an appointment in the healthcare system.
 */
public class Appointment {
    private Integer appointmentID;          // Unique identifier for the appointment
    private String patientEmail;            // Email of the patient associated with the appointment
    private String specialistEmail;         // Email of the specialist associated with the appointment
    private LocalDateTime dateTime;         // Date and time of the appointment
    private Integer amount;                 // Price or cost of the appointment
    private Boolean paid;                   // Indicates whether the appointment has been paid for
    private Integer paymentTrackNo;         // Tracking number for payment
    private LocalDateTime paymentDateTime;  // Date and time of payment

    /**
     * Constructor to initialize an Appointment object.
     *
     * @param appointmentID    Unique identifier for the appointment.
     * @param patientEmail     Email of the patient associated with the appointment.
     * @param specialistEmail  Email of the specialist associated with the appointment.
     * @param dateTime         Date and time of the appointment.
     * @param amount           Price or cost of the appointment.
     * @param paid             Indicates whether the appointment has been paid for.
     */
    public Appointment(Integer appointmentID, String patientEmail, String specialistEmail, LocalDateTime dateTime,
                       Integer amount, Boolean paid) {
        this.appointmentID = appointmentID;
        this.patientEmail = patientEmail;
        this.specialistEmail = specialistEmail;
        this.dateTime = dateTime;
        this.amount = amount;
        this.paid = paid;
    }

    // Getter and setter methods for the Appointment class

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
