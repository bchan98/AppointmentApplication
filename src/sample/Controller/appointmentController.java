package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.DAO.JDBC;
import sample.DAO.appointmentQuery;
import sample.model.appointment;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class appointmentController implements Initializable {
    public TableView appointmentDisplay;
    public TableColumn appTitleCol;
    public TableColumn appLocCol;
    public TableColumn appStartCol;
    public TableColumn appEndCol;
    public TableColumn appCIDCol;
    public TableColumn appAIDCol;
    public TableColumn appDesCol;
    public TableColumn appContCol;
    public TableColumn appTypeCol;
    public TableColumn appUIDCol;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<appointment> listAppointment = FXCollections.observableArrayList();
        JDBC.openConnection();
        try {
            listAppointment = appointmentQuery.getAllAppointments();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBC.closeConnection();

        appointmentDisplay.setItems(listAppointment);
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appLocCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        appCIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        appAIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appContCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appUIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
    }
}
