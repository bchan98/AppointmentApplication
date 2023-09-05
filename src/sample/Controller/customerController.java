package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.customerQuery;
import sample.model.customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
    public static int sendID = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<customer> showCustomers = FXCollections.observableArrayList();
        JDBC.openConnection();

        try {
            showCustomers = customerQuery.getAllCustomers();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBC.closeConnection();

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
    }

    public void modCustomer(ActionEvent actionEvent) throws IOException {
        isAdd = false;

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerModScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle ("Modify Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void delCustomer(ActionEvent actionEvent) {
    }
}
