package sample.model;

import java.time.ZonedDateTime;

public class appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private ZonedDateTime appointmentStart;
    private ZonedDateTime appointmentEnd;
    private ZonedDateTime appointmentCreationDate;
    private String appointmentCreationUser;
    private ZonedDateTime appointmentLastUpdate;
    private int customerID;
    private int userID;
    private int contactID;

    public appointment(int appointmentID, String title, String description, String location, String appointmentType, ZonedDateTime appointmentStart, ZonedDateTime appointmentEnd, ZonedDateTime appointmentCreationDate, String appointmentCreationUser, ZonedDateTime appointmentLastUpdate, int customerID, int userID, int contactID) {
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

    public ZonedDateTime getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(ZonedDateTime appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public ZonedDateTime getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(ZonedDateTime appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public ZonedDateTime getAppointmentCreationDate() {
        return appointmentCreationDate;
    }

    public void setAppointmentCreationDate(ZonedDateTime appointmentCreationDate) {
        this.appointmentCreationDate = appointmentCreationDate;
    }

    public String getAppointmentCreationUser() {
        return appointmentCreationUser;
    }

    public void setAppointmentCreationUser(String appointmentCreationUser) {
        this.appointmentCreationUser = appointmentCreationUser;
    }

    public ZonedDateTime getAppointmentLastUpdate() {
        return appointmentLastUpdate;
    }

    public void setAppointmentLastUpdate(ZonedDateTime appointmentLastUpdate) {
        this.appointmentLastUpdate = appointmentLastUpdate;
    }
}
