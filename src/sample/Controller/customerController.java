package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.customerQuery;
import sample.model.customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class customerController implements Initializable {
    public TableView customerDisplay;
    public TableColumn cusID;
    public TableColumn cusName;
    public TableColumn cusAdd;
    public TableColumn cusPC;
    public TableColumn cusPhone;
    public TableColumn cusDivID;

    public static boolean isAdd = false;
    public static customer sendCustomer;
    public Button exitButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> showCustomers = FXCollections.observableArrayList();

        try {
            JDBC.openConnection();
            showCustomers = customerQuery.getAllCustomers();
            JDBC.closeConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        customerDisplay.setItems(showCustomers);
        cusID.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        cusName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        cusAdd.setCellValueFactory(new PropertyValueFactory<>("address"));
        cusPC.setCellValueFactory(new PropertyValueFactory<>("postCode"));
        cusPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        cusDivID.setCellValueFactory(new PropertyValueFactory<>("divisionID"));
    }

    public void addCustomer(ActionEvent actionEvent) throws IOException {
        isAdd = true;

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerModScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle ("Add Customer");
        stage.setScene(scene);
        stage.show();

        Stage cuStage = (Stage) customerDisplay.getScene().getWindow();
        cuStage.close();
    }

    public void modCustomer(ActionEvent actionEvent) throws IOException {

        if(customerDisplay.getSelectionModel().getSelectedItem() == null) {
            Alert errorM = new Alert(Alert.AlertType.ERROR);
            errorM.setTitle("No items selected");
            errorM.setHeaderText("Missing items required to modify.");
            errorM.setContentText("Please ensure that you have selected a customer to be modified.");
            errorM.show();
        }
        else {
            isAdd = false;
            customer selCustomer = (customer) customerDisplay.getSelectionModel().getSelectedItem();
            sendCustomer = selCustomer;

            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerModScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 600);
            stage.setTitle ("Modify Customer");
            stage.setScene(scene);
            stage.show();

            Stage cuStage = (Stage) customerDisplay.getScene().getWindow();
            cuStage.close();
        }
    }

    public void delCustomer(ActionEvent actionEvent) throws SQLException {
        int toDelete = 0;
        JDBC.openConnection();
        if(customerDisplay.getSelectionModel().getSelectedItem() == null) {
            Alert errorM = new Alert(Alert.AlertType.ERROR);
            errorM.setTitle("No items selected");
            errorM.setHeaderText("Missing items required to delete.");
            errorM.setContentText("Please ensure that you have selected a customer to be deleted.");
            errorM.show();
        }
        else {
            if (customerQuery.findAppointments((customer) customerDisplay.getSelectionModel().getSelectedItem()) == 0) {
                customer delCustomer = (customer) customerDisplay.getSelectionModel().getSelectedItem();
                toDelete = delCustomer.getCustomerID();

                Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
                confDel.setTitle("Confirm deletion");
                confDel.setHeaderText("Deletion Warning");
                confDel.setContentText("Are you sure you want to delete this customer?");

                Optional<ButtonType> result = confDel.showAndWait();
                if (result.get() == ButtonType.OK) {
                    customerQuery.delete(toDelete);
                    ObservableList<customer> showCustomers = FXCollections.observableArrayList();
                    showCustomers = customerQuery.getAllCustomers();
                    customerDisplay.setItems(showCustomers);
                }
            }
            else {
                Alert cantDel = new Alert(Alert.AlertType.WARNING);
                cantDel.setTitle("Cannot delete this customer");
                cantDel.setHeaderText("Customer still has appointments.");
                cantDel.setContentText("This customer still has appointments planned in the system. Please delete all appointments with this customer before continuing.");
                cantDel.show();
            }
        }
        JDBC.closeConnection();
    }

    public void closeWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/menuScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle ("Main Menu");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) customerDisplay.getScene().getWindow();
        prevStage.close();
    }
}
