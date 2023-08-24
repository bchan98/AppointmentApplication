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

        while(rs.next()) {
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
}
