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
        return counter;
    }

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
        Timestamp nuLastUpdate = new  Timestamp(System.currentTimeMillis());
        String nuLastUpdateBy = loginController.loggedUser;

        if(provinceMenu.getValue() == null) {
            checkFlag = false;
        }
        else {
            JDBC.openConnection();
            nuID = 1 + getLastCID();
            nuDiv = converter.toDivisionID((String) provinceMenu.getValue());
            JDBC.closeConnection();
        }

        customer nuCustomer = new customer(nuID, nuName, nuAddress, nuPC, nuPhone, nuCreateDate, nuCreateBy, nuLastUpdate, nuLastUpdateBy, nuDiv);

        if(checkFlag) {
            JDBC.openConnection();
            customerQuery.create(nuCustomer);
            JDBC.closeConnection();

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
