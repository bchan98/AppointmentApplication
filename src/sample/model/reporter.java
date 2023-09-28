package sample.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.DAO.JDBC;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class reporter {

    public static void loginChecker(String attemptUser, String attemptPass, boolean valid) throws IOException {
        String inputData = null;
        String filename = "login_activity.txt";
        FileWriter writer = new FileWriter(filename, true);

        LocalDateTime curTime = LocalDateTime.now();
        String writeTime = curTime.toString();

        System.out.println(valid);

        if(valid) {
            inputData = "A login attempt was made at " + writeTime + " by " + attemptUser + " by inputting the correct password.";
        }
        else {
            inputData = "An unsuccesful login attempt was made at " + writeTime + " by " + attemptUser + " by inputting the incorrect password " + attemptPass + ".";
        }

        writer.write(inputData);
        writer.write(System.lineSeparator());
        writer.close();
    }

    public static ObservableList<String> getAppointmentReport() throws SQLException {
        ObservableList<String> listAppointments = FXCollections.observableArrayList();
        String sql = "SELECT DATE_FORMAT(start, '%m-%Y') AS Month, Type, COUNT(Appointment_ID) AS Amount FROM appointments GROUP BY MONTH(start), YEAR(start), Type";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String nuMonth = rs.getString("Month");
            String nuType = rs.getString("Type");
            int nuCount = rs.getInt("Amount");
            String nuShow = String.valueOf(nuCount);
            String total = "There are " + nuShow + " appointments of the " + nuType + " type, occurring in " + nuMonth + ".";
            listAppointments.add(total);
        }

        return listAppointments;
    }

    public static ObservableList<appointment> getContactAppointments() throws SQLException {
        ObservableList<appointment> contactAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS ORDER BY Contact_ID";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

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
            contactAppointments.add(nuAppointment);
        }
        return contactAppointments;
    }

    public static ObservableList<String> getCreationActivity() throws SQLException {
        ObservableList<String> creationActivity = FXCollections.observableArrayList();

        String sql = "SELECT User_ID AS User, DATE_FORMAT(Create_Date, '%m-%Y') AS Month, COUNT(Appointment_ID) AS Created FROM appointments GROUP BY MONTH(Create_Date), YEAR(Create_Date)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String nuMonth = rs.getString("Month");
            String nuUser = converter.toUserName(rs.getInt("User"));
            String nuCount = String.valueOf(rs.getInt("Created"));
            String finalString = "The user " + nuUser + " has created " + nuCount + " appointments in the month of " + nuMonth + ".";

            creationActivity.add(finalString);
        }

        return creationActivity;
    }
}
