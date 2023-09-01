package sample.model;

import sample.DAO.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class converter {
    public static int toUserID(String userName) throws SQLException {
        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        int subID = 0;

        while(rs.next()) {
            String checkName = rs.getString("User_Name");
            if(checkName == userName) {
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
            if(checkName == customerName) {
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
            if(checkName == contactName) {
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
}
