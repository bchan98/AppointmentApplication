package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class appointmentQuery {

    private static ObservableList<appointment> allAppointments = FXCollections.observableArrayList();

    public static ObservableList<appointment> getAllAppointments() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        allAppointments.clear();

        while (rs.next()) {
            int nuAppointmentID = rs.getInt("Appointment_ID");
            String nuTitle = rs.getString("Title");
            String nuDescription = rs.getString("Description");
            String nuLocation = rs.getString("Location");
            String nuType = rs.getString("Type");
            Timestamp nuStart = rs.getTimestamp("Start");
            Timestamp nuEnd = rs.getTimestamp("End");
            Timestamp nuCreateDate = rs.getTimestamp("Create_Date");
            String nuCreateBy = rs.getString("Created_By");
            Timestamp nuLastUpdate = rs.getTimestamp("Last_Update");
            String nuLastUpdatedBy = rs.getString("Last_Updated_By");
            int nuCustomerID = rs.getInt("Customer_ID");
            int nuUserID = rs.getInt("User_ID");
            int nuContactID = rs.getInt("Contact_ID");

            appointment nuAppointment = new appointment(nuAppointmentID, nuTitle, nuDescription, nuLocation, nuType, nuStart, nuEnd, nuCreateDate, nuCreateBy, nuLastUpdate, nuLastUpdatedBy, nuCustomerID, nuUserID, nuContactID);
            allAppointments.add(nuAppointment);
        }
        return allAppointments;
    }

    public static int create(appointment nuAppointment) throws SQLException {

        int nuAppointmentID = nuAppointment.getAppointmentID();
        String nuTitle = nuAppointment.getTitle();
        String nuDescription = nuAppointment.getDescription();
        String nuLocation = nuAppointment.getLocation();
        String nuType = nuAppointment.getAppointmentType();
        Timestamp nuStart = nuAppointment.getAppointmentStart();
        Timestamp nuEnd = nuAppointment.getAppointmentEnd();
        Timestamp nuCreateDate = nuAppointment.getAppointmentCreationDate();
        String nuCreateBy = nuAppointment.getAppointmentCreationUser();
        Timestamp nuLastUpdate = nuAppointment.getAppointmentLastUpdate();
        String nuLastUpdatedBy = nuAppointment.getAppointmentLastUpdatedBy();
        int nuCustomerID = nuAppointment.getCustomerID();
        int nuUserID = nuAppointment.getUserID();
        int nuContactID = nuAppointment.getContactID();


        String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, nuAppointmentID);
        ps.setString(2, nuTitle);
        ps.setString(3, nuDescription);
        ps.setString(4, nuLocation);
        ps.setString(5, nuType);
        ps.setTimestamp(6, nuStart);
        ps.setTimestamp(7, nuEnd);
        ps.setTimestamp(8, nuCreateDate);
        ps.setString(9, nuCreateBy);
        ps.setTimestamp(10, nuLastUpdate);
        ps.setString(11, nuLastUpdatedBy);
        ps.setInt(12, nuCustomerID);
        ps.setInt(13, nuUserID);
        ps.setInt(14, nuContactID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /**
    public static int remove(appointment nuAppointment) throws SQLException {

    }
    **/
}

