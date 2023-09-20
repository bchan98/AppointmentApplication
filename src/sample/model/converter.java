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

    public static LocalDateTime toUserTime(Timestamp time) {
        ZoneId compTime = ZoneId.systemDefault();
        Instant curTime = time.toInstant();
        ZonedDateTime tempTime = curTime.atZone(compTime);
        LocalDateTime sysTime = tempTime.toLocalDateTime();
        return sysTime;
    }

    public static ZonedDateTime toCompanyTime(Timestamp time) {
        LocalDateTime conOne = time.toLocalDateTime();
        ZonedDateTime companyTime = conOne.atZone(ZoneId.systemDefault());
        return companyTime;
    }

    public static Timestamp toUniversalTime(LocalDateTime time) {
        Instant nuTime1 = time.toInstant(ZoneOffset.UTC);
        Timestamp universalTime = Timestamp.from(nuTime1);
        return universalTime;
    }

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
