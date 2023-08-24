package sample.model;


import java.sql.Timestamp;

public class appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String appointmentType;
    private Timestamp appointmentStart;
    private Timestamp appointmentEnd;
    private Timestamp appointmentCreationDate;
    private String appointmentCreationUser;
    private Timestamp appointmentLastUpdate;
    private String appointmentLastUpdatedBy;
    private int customerID;
    private int userID;
    private int contactID;

    public appointment(int appointmentID, String title, String description, String location, String appointmentType, Timestamp appointmentStart, Timestamp appointmentEnd, Timestamp appointmentCreationDate, String appointmentCreationUser, Timestamp appointmentLastUpdate, String appointmentLastUpdatedBy, int customerID, int userID, int contactID) {
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
        this.appointmentLastUpdatedBy = appointmentLastUpdatedBy;
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

    public Timestamp getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(Timestamp appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public Timestamp getAppointmentEnd() {
        return appointmentEnd;
    }

    public void setAppointmentEnd(Timestamp appointmentEnd) {
        this.appointmentEnd = appointmentEnd;
    }

    public Timestamp getAppointmentCreationDate() {
        return appointmentCreationDate;
    }

    public void setAppointmentCreationDate(Timestamp appointmentCreationDate) {
        this.appointmentCreationDate = appointmentCreationDate;
    }

    public String getAppointmentCreationUser() {
        return appointmentCreationUser;
    }

    public void setAppointmentCreationUser(String appointmentCreationUser) {
        this.appointmentCreationUser = appointmentCreationUser;
    }

    public Timestamp getAppointmentLastUpdate() {
        return appointmentLastUpdate;
    }

    public void setAppointmentLastUpdate(Timestamp appointmentLastUpdate) {
        this.appointmentLastUpdate = appointmentLastUpdate;
    }

    public String getAppointmentLastUpdatedBy() {
        return appointmentLastUpdatedBy;
    }

    public void setAppointmentLastUpdatedBy(String appointmentLastUpdatedBy) {
        this.appointmentLastUpdatedBy = appointmentLastUpdatedBy;
    }
}
