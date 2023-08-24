package sample.model;

import java.time.LocalDateTime;

public class appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private LocalDateTime appointmentStart;
    private LocalDateTime appointmentEnd;
    private LocalDateTime appointmentCreationDate;
    private String appointmentCreationUser;
    private LocalDateTime appointmentLastUpdate;
    private int customerID;
    private int userID;
    private int contactID;

    public appointment(int appointmentID, String title, String description, String location, String appointmentType, LocalDateTime appointmentStart, LocalDateTime appointmentEnd, LocalDateTime appointmentCreationDate, String appointmentCreationUser, LocalDateTime appointmentLastUpdate, int customerID, int userID, int contactID) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.appointmentType = appointmentType;
        this.appointmentStart = appointmentStart;
        this.appointmentEnd = appointmentEnd;
        this.appointmentCreationDate = appointmentCreationDate;
        this.appointmentCreationUser = appointmentCreationUser;
        this.appointmentLastUpdate = appointmentLastUpdate;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public LocalDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(LocalDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public LocalDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(LocalDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public LocalDateTime getAppointmentCreationDate() {
        return appointmentCreationDate;
    }

    public void setAppointmentCreationDate(LocalDateTime appointmentCreationDate) {
        this.appointmentCreationDate = appointmentCreationDate;
    }

    public String getAppointmentCreationUser() {
        return appointmentCreationUser;
    }

    public void setAppointmentCreationUser(String appointmentCreationUser) {
        this.appointmentCreationUser = appointmentCreationUser;
    }

    public LocalDateTime getAppointmentLastUpdate() {
        return appointmentLastUpdate;
    }

    public void setAppointmentLastUpdate(LocalDateTime appointmentLastUpdate) {
        this.appointmentLastUpdate = appointmentLastUpdate;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
        this.contactID = contactID;
    }
}
