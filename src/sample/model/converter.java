package sample.model;

import com.mysql.cj.x.protobuf.MysqlxPrepare;
import javafx.collections.ObservableList;
import sample.DAO.JDBC;
import sample.DAO.customerQuery;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;

public class converter {
    /** This method converts a username to a user ID. Checks for users name and converts to ID.
     *
     * @param userName The username to be converted.
     * @return Returns the user's ID.
     * @throws SQLException
     */
    public static int toUserID(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("User_Name");
            if(checkName.equals(userName)) {
                subID = rs.getInt("User_ID");
            }
        }

        return subID;
    }

    /** This method converts a user ID to a user name. Checks for user ID and converts to name.
     *
     * @param userID The user ID to be converted.
     * @return Returns the user's name.
     * @throws SQLException
     */
    public static String toUserName(int userID) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String subName = null;

        while(rs.next()) {
            int checkID = rs.getInt("User_ID");
            if(checkID == userID) {
                subName = rs.getString("User_Name");
            }
        }
        return subName;

    }

    /** This method converts a customer name to a customer ID. Checks for customer name and converts to ID.
     *
     * @param customerName The customer name to be converted.
     * @return Returns the customer ID.
     * @throws SQLException
     */
    public static int toCustomerID(String customerName) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("Customer_Name");
            if(checkName.equals(customerName)) {
                subID = rs.getInt("Customer_ID");
            }
        }

        return subID;
    }

    /** This method converts a customer ID to a customer name. Checks for customer ID and converts to name.
     *
     * @param customerID The customer ID to be converted.
     * @return Returns the customer name.
     * @throws SQLException
     */
    public static String toCustomerName(int customerID) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String subName = null;

        while(rs.next()) {
            int checkID = rs.getInt("Customer_ID");
            if(checkID == customerID) {
                subName = rs.getString("Customer_Name");
            }
        }

        return subName;
    }

    /** This method converts a contact name to a contact ID. Checks for contact name and converts to a ID.
     *
     * @param contactName The contact name to be converted.
     * @return Returns the contact ID.
     * @throws SQLException
     */
    public static int toContactID(String contactName) throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("Contact_Name");
            if(checkName.equals(contactName)) {
                subID = rs.getInt("Contact_ID");
            }
        }

        return subID;
    }

    /** This method converts a contact ID to a contact name. Checks for contact ID and converts to name.
     *
     * @param contactID The contact ID to be converted.
     * @return Returns the contact name.
     * @throws SQLException
     */
    public static String toContactName(int contactID) throws SQLException {
        String sql = "SELECT * FROM CONTACTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String subName = null;

        while(rs.next()) {
            int checkID = rs.getInt("Contact_ID");
            if(checkID == contactID) {
                subName = rs.getString("Contact_Name");
            }
        }

        return subName;
    }

    /** This method converts a timestamp to the user's time. Takes the timestamp, converts from UTC to system timezone.
     *
     * @param time The timestamp to be converted
     * @return Returns the converted time in the system's timezone.
     */
    public static LocalDateTime toUserTime(Timestamp time) {
        ZoneId compTime = ZoneId.systemDefault();
        ZoneId universalZone = ZoneId.of("UTC");

        LocalDateTime preConversion = time.toLocalDateTime();
        ZonedDateTime beforeConversion = ZonedDateTime.of(preConversion, universalZone);
        ZonedDateTime tempTime = beforeConversion.withZoneSameInstant(compTime);
        LocalDateTime sysTime = tempTime.toLocalDateTime();
        return sysTime;
    }

    /** This method converts a timestamp to the company's time. Takes the timestamp, converts from UTC to EST timezone.
     *
     * @param time The timestamp to be converted.
     * @return Returns the converted time in EST.
     */
    public static LocalDateTime toCompanyTime(Timestamp time) {
        ZoneId companyTimeZone = ZoneId.of("US/Eastern");
        ZoneId universalZone = ZoneId.of("UTC");

        LocalDateTime pretempTime = time.toLocalDateTime();
        ZonedDateTime tempTime = ZonedDateTime.of(pretempTime, universalZone);
        ZonedDateTime convertedTime = tempTime.withZoneSameInstant(companyTimeZone);
        LocalDateTime companyTime = convertedTime.toLocalDateTime();
        return companyTime;
    }

    /** This method converts a LocalDateTime timestamp to UTC timestamp. The LocalDateTime is converted to ZoneDateTime, had timezone adjustment, then turned into a timestamp.
     *
     * @param time
     * @return
     */
    public static Timestamp toUniversalTime(LocalDateTime time) {
        ZoneId universalZone = ZoneId.of("UTC");
        ZonedDateTime unconvertedTime = time.atZone(ZoneId.systemDefault());
        ZonedDateTime convertedTime = unconvertedTime.withZoneSameInstant(universalZone);
        LocalDateTime preTimestamp = convertedTime.toLocalDateTime();
        Timestamp universalTime = Timestamp.valueOf(preTimestamp);
        return universalTime;
    }

    /** This method converts a country name to a country ID. The country name is checked in the MySQL database and converted.
     *
     * @param countryName The country name to be converted
     * @return Returns the country ID
     * @throws SQLException
     */
    public static int toCountryID(String countryName) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("Country");
            if(checkName.equals(countryName)) {
                subID = rs.getInt("Country_ID");
            }
        }
        return subID;
    }

    /** This method converts a country ID to a country name. The country ID is checked in the MySQL database and converted.
     *
     * @param countryID The country ID to be converted
     * @return Returns the country name.
     * @throws SQLException
     */
    public static String toCountryName(int countryID) throws SQLException {
        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String subName = null;

        while(rs.next()) {
            int checkID = rs.getInt("Country_ID");
            if(checkID == countryID) {
                subName = rs.getString("Country");
            }
        }

        return subName;
    }

    /** This method converts a division name to a division ID. The division name is checked in the MySQL database and converted.
     *
     * @param divisionName The division name to be converted.
     * @return Returns the division ID.
     * @throws SQLException
     */
    public static int toDivisionID(String divisionName) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("Division");
            if(divisionName.equals(checkName)) {
                subID = rs.getInt("Division_ID");
            }
        }
        return subID;
    }

    /** This method converts a division ID to a division name. The division ID is checked in the MySQL database and converted.
     *
     * @param divisionID The division ID to be converted.
     * @return Returns the division name.
     * @throws SQLException
     */
    public static String toDivisionName(int divisionID) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        String subName = null;

        while(rs.next()) {
            int checkID = rs.getInt("Division_ID");
            if(checkID == divisionID) {
                subName = rs.getString("Division");
            }
        }

        return subName;
    }

    /** This method gets the country ID from the division ID. The division ID is checked in the MySQL database and retrieves country ID information.
     *
     * @param divisionID The division ID to be searched.
     * @return Returns the country ID.
     * @throws SQLException
     */
    public static int getCountryID(int divisionID) throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            int checkID = rs.getInt("Division_ID");
            if(checkID == divisionID) {
                subID = rs.getInt("Country_ID");
            }
        }
        return subID;
    }
}
