package sample.DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.model.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class customerQuery {
    private static ObservableList<customer> allCustomers = FXCollections.observableArrayList();
    private static ObservableList<String> allCountries = FXCollections.observableArrayList();
    private static ObservableList<String> allDivisions = FXCollections.observableArrayList();

    public static ObservableList<customer> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);
        allCustomers.clear();

        while(rs.next()) {
            int nuCusID = rs.getInt("Customer_ID");
            String nuCusName = rs.getString("Customer_Name");
            String nuAddress = rs.getString("Address");
            String nuPC = rs.getString("Postal_Code");
            String nuPhone = rs.getString("Phone");
            Timestamp nuCreateDate = rs.getTimestamp("Create_Date");
            String nuCreateBy = rs.getString("Created_By");
            Timestamp nuLastUpdate = rs.getTimestamp("Last_Update");
            String nuLastUpdateBy = rs.getString("Last_Updated_By");
            int nuDivID = rs.getInt("Division_ID");

            customer nuCustomer = new customer(nuCusID, nuCusName, nuAddress, nuPC, nuPhone, nuCreateDate, nuCreateBy, nuLastUpdate, nuLastUpdateBy, nuDivID);

            allCustomers.add(nuCustomer);
        }

        return allCustomers;
    }

    public static ObservableList<String> getCountries() throws SQLException {
        allCountries.clear();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            String nuCountry = rs.getString("Country");
            allCountries.add(nuCountry);
        }
        return allCountries;
    }

    public static ObservableList<String> getDivisions(int countID) throws SQLException {
        allDivisions.clear();

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()) {
            int cIDCheck = rs.getInt("Country_ID");
            if (cIDCheck == countID) {
                String nuDivision = rs.getString("Division");
                allDivisions.add(nuDivision);
            }
        }

        return allDivisions;
    }
}
