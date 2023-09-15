package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentQuery;
import sample.model.appointment;

import java.io.IOException;
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
    public RadioButton weekSelect;
    public ToggleGroup viewBy;
    public RadioButton monthSelect;
    public static boolean isAdd = true;
    public static appointment sendAppointment;

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

    public void makeAppointment(ActionEvent actionEvent) throws IOException {
        isAdd = true;

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentModScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle ("Create an Appointment");
        stage.setScene(scene);
        stage.show();

        Stage curStage = (Stage) appointmentDisplay.getScene().getWindow();
        curStage.close();
    }

    public void updateAppointment(ActionEvent actionEvent) throws IOException {
        isAdd = false;
        sendAppointment = (appointment) appointmentDisplay.getSelectionModel().getSelectedItem();

        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentModScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle ("Modify an Appointment");
        stage.setScene(scene);
        stage.show();

        Stage curStage = (Stage) appointmentDisplay.getScene().getWindow();
        curStage.close();
    }

    public void deleteAppointment(ActionEvent actionEvent) {
    }

    public void viewWeek(ActionEvent actionEvent) {
    }

    public void viewMonth(ActionEvent actionEvent) {
    }

    public void regressView(ActionEvent actionEvent) {
    }

    public void advanceView(ActionEvent actionEvent) {
    }

    public void exitWindow(ActionEvent actionEvent) {
    }
}
