package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.customerQuery;
import sample.model.converter;
import sample.model.customer;

import java.io.IOException;
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
    public Button saveButton;
    public Button closeButton;

    private static Timestamp findCreate;
    private static String findCreateBy;

    private int countryIDFlag = 0;
    private static ObservableList<String> allCountries = FXCollections.observableArrayList("U.S", "UK","Canada");

    /** This method populates the provinceMenu with the appropriate first level divisions. Upon an item being selected in countryMenu, a sql call is made to retrieve the appropriate FLDs.
     *
     * @param actionEvent Triggers upon selection of an item in the countryMenu combobox.
     * @throws SQLException
     */
    public void countrySel(ActionEvent actionEvent) throws SQLException {
        String selected = (String) countryMenu.getValue();
        JDBC.openConnection();
        int selectedID = converter.toCountryID(selected);
        System.out.println(selectedID);
        provinceMenu.setItems(customerQuery.getDivisions(selectedID));
        JDBC.closeConnection();
    }

    /** This method initializes the window. Fills in the appropriate information into each field where neccessary if adding or modifying a pre-existing customer.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){

        // check if add or modifying a customer
        if(customerController.isAdd) {
            try {
                JDBC.openConnection();
                IDField.setText(String.valueOf(getLastCID() + 1));
                countryMenu.setItems(customerQuery.getCountries());
                JDBC.closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
             try {
                JDBC.openConnection();
                IDField.setText(String.valueOf(customerController.sendCustomer.getCustomerID()));
                nameField.setText(customerController.sendCustomer.getCustomerName());
                addressField.setText(customerController.sendCustomer.getAddress());
                phoneField.setText(customerController.sendCustomer.getPhone());
                posCodeField.setText(customerController.sendCustomer.getPostCode());

                int thisDiv = customerController.sendCustomer.getDivisionID();
                int thisCon = converter.getCountryID(thisDiv);
                String conName = converter.toCountryName(thisCon);
                String divName = converter.toDivisionName(thisDiv);

                countryMenu.setItems(allCountries);
                countryMenu.setValue(conName);
                provinceMenu.setItems(customerQuery.getDivisions(thisCon));
                provinceMenu.setValue(divName);

                findCreate = customerController.sendCustomer.getCreateDate();
                findCreateBy = customerController.sendCustomer.getCreateBy();
                JDBC.closeConnection();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /** This method finds the last customer ID. Makes a SQL call to determine the latest customer created and their customerID.
     *
     * @return Returns counter, the last customerID.
     * @throws SQLException
     */
    public int getLastCID() throws SQLException {
        int counter = 0;
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            counter = rs.getInt("Customer_ID");
        }
        return counter;
    }

    /** This method saves any changes made to a customer. Makes a series of logic checks determining first if the customer is a new or existing customer, and then carries out logic checks to ensure inputted information is valid.
     *
     * @param actionEvent Triggers upon pressing the saveButton.
     * @throws SQLException
     * @throws IOException
     */
    public void saveChanges(ActionEvent actionEvent) throws SQLException, IOException {
        boolean checkFlag = true;
        int nuDiv = -1;
        int nuID = 0;

        String nuName = nameField.getText();
        String nuAddress = addressField.getText();
        String nuPC = posCodeField.getText();
        String nuPhone = phoneField.getText();

        if (nuName.trim().isEmpty() || nuAddress.trim().isEmpty() || nuPC.trim().isEmpty() || nuPhone.trim().isEmpty())
        {
            checkFlag = false;
        }

        Timestamp nuCreateDate = new Timestamp(System.currentTimeMillis());
        String nuCreateBy = loginController.loggedUser;

        if(customerController.isAdd) {
            nuCreateDate = findCreate;
            nuCreateBy = findCreateBy;
        }

        Timestamp nuLastUpdate = new  Timestamp(System.currentTimeMillis());
        String nuLastUpdateBy = loginController.loggedUser;

        if(provinceMenu.getValue() == null) {
            checkFlag = false;
        }
        else {
            JDBC.openConnection();
            if(customerController.isAdd) {
                nuID = 1 + getLastCID();
            }
            else {
                nuID = Integer.parseInt(IDField.getText());
            }
            nuDiv = converter.toDivisionID((String) provinceMenu.getValue());
            JDBC.closeConnection();
        }

        customer nuCustomer = new customer(nuID, nuName, nuAddress, nuPC, nuPhone, nuCreateDate, nuCreateBy, nuLastUpdate, nuLastUpdateBy, nuDiv);

        if(checkFlag) {
            if(customerController.isAdd) {
                JDBC.openConnection();
                customerQuery.create(nuCustomer);
                JDBC.closeConnection();
                System.out.println("This is a creation!");

            }
            else {
                JDBC.openConnection();;
                int affected = customerQuery.update(nuCustomer);
                JDBC.closeConnection();
                System.out.println("This is a modification!");
                System.out.println(affected);
            }

            System.out.println("Success!");

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle ("Customers");
            stage.setScene(scene);
            stage.show();

            Stage curStage = (Stage) saveButton.getScene().getWindow();
            curStage.close();
        }
        else {
            Alert errorM = new Alert(Alert.AlertType.ERROR);
            errorM.setTitle("Missing parameters");
            errorM.setHeaderText("Some parameters have not been met.");
            errorM.setContentText("Please check to see if all fields have been properly inputted with information.");
            errorM.show();
        }
    }

    /** This method closes the window.
     *
     * @param actionEvent Triggers upon pressing the exitButton.
     * @throws IOException
     */
    public void closeWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Customers");
        stage.setScene(scene);
        stage.show();

        Stage curStage = (Stage) saveButton.getScene().getWindow();
        curStage.close();
    }
}
