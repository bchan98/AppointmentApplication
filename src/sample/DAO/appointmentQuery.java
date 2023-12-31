package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Controller.loginController;
import sample.model.appointment;
import sample.model.converter;

import java.sql.*;
import java.time.*;

public class appointmentQuery {

    private static ObservableList<appointment> allAppointments = FXCollections.observableArrayList();
    private static ObservableList<appointment> checkWeek = FXCollections.observableArrayList();
    private static ObservableList<appointment> checkMonth = FXCollections.observableArrayList();
    private static ObservableList<String> allContacts = FXCollections.observableArrayList();
    private static ObservableList<String> allCustomerNames = FXCollections.observableArrayList();

    /** This method pulls up all appointments in the MySQL database. All appointments existing within the MySQL database are selected and then information is put into appointment objects.
     *
     * @return Returns a list containing all appointments within the system.
     * @throws SQLException
     */
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

    /** This method pulls up all appointments within a week of the selected date. All appointments existing within a week of the selected date are retrieved from the MySQL database are selected and then information is put into appointment objects.
     *
     * @param nuDate A LocalDate object passed to determine which date range should be selected.
     * @return Returns a list containing all appointments within a week of the selected date found in the MySQL database.
     * @throws SQLException
     */
    public static ObservableList<appointment> getWeeklyAppointments(LocalDate nuDate) throws SQLException {
        // advance/regress dates as needed
        LocalDate newStart = nuDate.minusDays(1);
        LocalDate newEnd = nuDate.plusDays(8);

        // execute string
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        checkWeek.clear();

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
            // check if date falls between required values
            LocalDate checkStart = nuStart.toLocalDateTime().toLocalDate();
            LocalDate checkEnd = nuEnd.toLocalDateTime().toLocalDate();
            if(checkStart.isAfter(newStart) && checkEnd.isBefore(newEnd)) {
                checkWeek.add(nuAppointment);
            }
        }
        return checkWeek;
    }

    /** This method pulls up all appointments within a month of the selected date. All appointments existing within a month of the selected date are retrieved from the MySQL database are selected and then information is put into appointment objects.
     *
     * @param nuDate A LocalDate object passed to determine which date range should be selected.
     * @return Returns a list containing all appointments within a month of the selected date found in the MySQL database.
     * @throws SQLException
     */
    public static ObservableList<appointment> getMonthlyAppointments(LocalDate nuDate) throws SQLException {
        // advance/regress dates as needed
        LocalDate newStart = nuDate.minusDays(1);
        LocalDate newEnd = nuDate.plusDays(30);

        // execute string
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        checkMonth.clear();

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
            // check if end and start fall between date values
            LocalDate checkStart = nuStart.toLocalDateTime().toLocalDate();
            LocalDate checkEnd = nuEnd.toLocalDateTime().toLocalDate();
            if(checkStart.isAfter(newStart) && checkEnd.isBefore(newEnd)) {
                checkMonth.add(nuAppointment);
            }
        }
        return checkMonth;
    }

    /** This method adds an appointment into the MySQL database. Information is gathered from the passed appointment and sent to the database via a MySQL query.
     *
     * @param nuAppointment The appointment to be added into the system.
     * @return Returns the number of rows created.
     * @throws SQLException
     */
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

    /** This method modifies an appointment into the MySQL database. Information is gathered from the passed appointment and sent to the database via a MySQL query.
     *
     * @param nuAppointment The appointment to be modified.
     * @return Returns the number of rows modified.
     * @throws SQLException
     */
    public static int update(appointment nuAppointment) throws SQLException {
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

        String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, nuTitle);
        ps.setString(2, nuDescription);
        ps.setString(3, nuLocation);
        ps.setString(4, nuType);
        ps.setTimestamp(5, nuStart);
        ps.setTimestamp(6, nuEnd);
        ps.setTimestamp(7, nuLastUpdate);
        ps.setString(8, nuLastUpdatedBy);
        ps.setInt(9, nuCustomerID);
        ps.setInt(10, nuUserID);
        ps.setInt(11, nuContactID);
        ps.setInt(12, nuAppointmentID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method removes an appointment from the MySQL database. The selected appointment's ID is passed and then removed via a MySQL query.
     *
     * @param appointmentID The ID of the appointment to be removed.
     * @return Returns the number of rows removed.
     * @throws SQLException
     */
    public static int delete(int appointmentID) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This retrieves all contact names from the MySQL database. Contact names are retrieved as strings and returned in an ObservableList.
     *
     * @return Returns a list of all the names of contacts from the MySQL database.
     * @throws SQLException
     */
    public static ObservableList<String> getAllContacts() throws SQLException {
        allContacts.clear();

        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String nuContact = rs.getString("Contact_Name");
            allContacts.add(nuContact);
        }

        return allContacts;
    }

    /** This retrieves all customer names from the MySQL database. Customer names are retrieved as strings and returned in an ObservableList.
     *
     * @return Returns a list of all the names of customers from the MySQL database.
     * @throws SQLException
     */
    public static ObservableList<String> getAllCustomerNames() throws SQLException {
        allCustomerNames.clear();

        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            String nuContact = rs.getString("Customer_Name");
            allCustomerNames.add(nuContact);
        }

        return allCustomerNames;
    }

    /** This method determines if an overlap exists within appointment times of any new appointment. Appointment start/end times are checked against existing appointment times to see if any overlap exists.
     *
     * @param checkAppointment The appointment which times are to be checked against existing appointments.
     * @return Returns a true/false boolean whether the new appointment can be made or not.
     * @throws SQLException
     */
    public static boolean checkOverlap(appointment checkAppointment) throws SQLException {
        boolean check = true;
        int findCus = checkAppointment.getCustomerID();

        // break down dates into components.
        Timestamp startT = checkAppointment.getAppointmentStart();
        Timestamp endT = checkAppointment.getAppointmentEnd();

        LocalDateTime startDate = converter.toUserTime(startT);
        LocalDateTime endDate = converter.toUserTime(endT);

        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, findCus);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            // break down dates into components
            Timestamp checkS = rs.getTimestamp("Start");
            Timestamp checkE = rs.getTimestamp("End");

            LocalDateTime checkStart = converter.toUserTime(checkS);
            LocalDateTime checkEnd = converter.toUserTime(checkE);

            if((checkStart.isBefore(startDate) && checkEnd.isAfter(startDate)) || (checkStart.isAfter(startDate) && checkStart.isBefore(startDate)) || (checkStart.isBefore(startDate) && checkEnd.isAfter(endDate)) || (checkStart.isAfter(startDate) && checkEnd.isBefore(endDate)) || (checkStart.isEqual(startDate)) || (checkEnd.isEqual(endDate))) {
                check = false;
            }
        }

        return check;
    }

    /** This method determines if an appointment has been made within company hours of 800 and 2200 EST. Appointment start/end times are checked against these numbers.
     *
     * @param checkAppointment The appointment which times are to be checked against existing appointments.
     * @return Returns a true/false boolean whether the new appointment can be made or not.
     */
    public static boolean checkCompanyTime(appointment checkAppointment) {
        boolean check = true;
        // grab appointment start and end times, convert to company hours
        Timestamp startT = checkAppointment.getAppointmentStart();
        Timestamp endT = checkAppointment.getAppointmentEnd();
        LocalDateTime startDateTime = converter.toCompanyTime(startT);
        LocalDateTime endDateTime = converter.toCompanyTime(endT);
        // convert start and end times to localtime
        LocalTime startDate = startDateTime.toLocalTime();
        LocalTime endDate = endDateTime.toLocalTime();
        // set company hours of 8 am and 10 pm
        LocalTime checkStart = LocalTime.of(8, 0);
        LocalTime checkEnd = LocalTime.of(22, 0);
        // check if falls between company hours
        if ((startDate.isAfter(checkStart) && endDate.isBefore(checkEnd))) {
            check = true;
        }
        else {
            check = false;
        }

        return check;
    }

    /** This method checks to see if an appointment is upcoming for the user. The username is passed from the loginController and checked to see if any appointments exist within a 15 minute window.
     *
     * @param curTime the current time to check.
     * @return If an appointment is incoming, the appointment information is returned.
     * @throws SQLException
     */
    public static appointment checkRecent(LocalDateTime curTime) throws SQLException {
        appointment  returnAppointment = new appointment(0, null, null, null, null, null, null, null, null, null, null, 0, 0, 0);
        int userID = converter.toUserID(loginController.loggedUser);
        LocalDateTime afterTime = curTime.plusMinutes(15);

        String sql = "SELECT * FROM APPOINTMENTS WHERE User_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            Timestamp appointStart = rs.getTimestamp("Start");
            LocalDateTime checkTime = converter.toUserTime(appointStart);
            if (checkTime.isAfter(curTime) && checkTime.isBefore(afterTime)) {
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
                returnAppointment = nuAppointment;
            }
        }
        return returnAppointment;
    }


}

