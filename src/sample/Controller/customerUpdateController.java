package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.DAO.JDBC;
import sample.DAO.customerQuery;
import sample.model.converter;
import sample.model.customer;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class customerUpdateController implements Initializable {
    public TextField nameField;
    public TextField addressField;
    public TextField posCodeField;
    public TextField phoneField;
    public ComboBox countryMenu;
    public ComboBox provinceMenu;
    public TextField IDField;

    private int countryIDFlag = 0;

    public void countrySel(ActionEvent actionEvent) throws SQLException {
        String selected = (String) countryMenu.getValue();
        JDBC.openConnection();
        int selectedID = converter.toCountryID(selected);
        System.out.println(selectedID);
        provinceMenu.setItems(customerQuery.getDivisions(selectedID));
        JDBC.closeConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        JDBC.openConnection();
        try {
            countryMenu.setItems(customerQuery.getCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(customerController.isAdd) {
            try {
                IDField.setText(String.valueOf(getLastCID() + 1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        JDBC.closeConnection();
    }

    public int getLastCID() throws SQLException {
        int counter = 0;
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            counter = rs.getInt("Customer_ID");
        }
        JDBC.closeConnection();
        return counter;
    }

    public void saveChanges(ActionEvent actionEvent) throws SQLException {
        String nuName = nameField.getText();
        String nuAddress = addressField.getText();
        String nuPC = posCodeField.getText();
        String nuPhone = phoneField.getText();
        JDBC.openConnection();
        int nuID = 1 + getLastCID();
        int nuDiv = converter.toDivisionID((String) provinceMenu.getValue());
        JDBC.closeConnection();
        Timestamp nuCreateDate = new Timestamp(System.currentTimeMillis());
        String nuCreateBy = loginController.loggedUser;
        Timestamp nuLastUpdate = new  Timestamp(System.currentTimeMillis());
        String nuLastUpdateBy = loginController.loggedUser;

        customer nuCustomer = new customer(nuID, nuName, nuAddress, nuPC, nuPhone, nuCreateDate, nuCreateBy, nuLastUpdate, nuLastUpdateBy, nuDiv);
    }

    public void closeWindow(ActionEvent actionEvent) {
    }
}
