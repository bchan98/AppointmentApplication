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

    /** This method obtains all customers in the MySQL database. All customers are retrieved, stored in customer objects and returned in an ObservableList.
     *
     * @return Returns a list of all customers.
     * @throws SQLException
     */
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

    /** This method returns all countries in the MySQL database. All countries are retrieved as strings and returned in an ObservableList.
     *
     * @return Returns a list of all countries.
     * @throws SQLException
     */
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

    /** This method returns all first level divisions in the MySQL database. All first level divisions are retrieved as strings and returned in an ObservableList.
     *
     * @return Returns a list of all first level divisions.
     * @throws SQLException
     */
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

    /** This method adds a customer to the MySQL database. Customer information is retrieved from a customer object and added via a MySQL query.
     *
     * @param nuCustomer The customer which is to be added to the MySQL database.
     * @return Returns the number of rows added.
     * @throws SQLException
     */
    public static int create(customer nuCustomer) throws SQLException {
        int nuID = nuCustomer.getCustomerID();
        String nuName = nuCustomer.getCustomerName();
        String nuAddress = nuCustomer.getAddress();
        String nuPC = nuCustomer.getPostCode();
        String nuPhone = nuCustomer.getPhone();
        Timestamp nuCreateDate = nuCustomer.getCreateDate();
        String nuCreateBy = nuCustomer.getCreateBy();
        Timestamp nuLastUpdate = nuCustomer.getLastUpdate();
        String nuLastUpdateBy = nuCustomer.getLastUpdateBy();
        int nuDiv = nuCustomer.getDivisionID();

        String sql = "INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1,nuID);
        ps.setString(2, nuName);
        ps.setString(3, nuAddress);
        ps.setString(4, nuPC);
        ps.setString(5, nuPhone);
        ps.setTimestamp(6, nuCreateDate);
        ps.setString(7, nuCreateBy);
        ps.setTimestamp(8, nuLastUpdate);
        ps.setString(9, nuLastUpdateBy);
        ps.setInt(10, nuDiv);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method removes a customer from the MySQL database. The customer is removed via a MySQL query.
     *
     * @param cID The customer ID of the customer to be removed.
     * @return Returns the number of rows removed.
     * @throws SQLException
     */
    public static int delete(int cID) throws SQLException {
        String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setInt(1, cID);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method modifies a customer in the MySQL database. Customer information is retrieved from a customer object and modified via a MySQL query.
     *
     * @param nuCustomer The customer which is to be modified in the database.
     * @return Returns the number of rows modified.
     * @throws SQLException
     */
    public static int update(customer nuCustomer) throws SQLException {
        int nuID = nuCustomer.getCustomerID();
        String nuName = nuCustomer.getCustomerName();
        String nuAddress = nuCustomer.getAddress();
        String nuPC = nuCustomer.getPostCode();
        String nuPhone = nuCustomer.getPhone();
        Timestamp nuLastUpdate = nuCustomer.getLastUpdate();
        String nuLastUpdateBy = nuCustomer.getLastUpdateBy();
        int nuDiv = nuCustomer.getDivisionID();

        String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.setString(1, nuName);
        ps.setString(2, nuAddress);
        ps.setString(3, nuPC);
        ps.setString(4, nuPhone);
        ps.setTimestamp(5, nuLastUpdate);
        ps.setString(6, nuLastUpdateBy);
        ps.setInt(7, nuDiv);
        ps.setInt(8, nuID);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method finds pre-existing appointments for the selected customer. All appointments are searched and counts total amount of appointments relating to the specific customer.
     *
     * @param nuCustomer The customer which is to be searched for in the database.
     * @return
     * @throws SQLException
     */
    public static int findAppointments(customer nuCustomer) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS";
        int appointmentCount = 0;
        int thisID = nuCustomer.getCustomerID();
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int checkID = rs.getInt("Customer_ID");
            if(checkID == thisID) {
                appointmentCount++;
            }
        }
        return appointmentCount;
    }
}
