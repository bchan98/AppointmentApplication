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
import sample.DAO.appointmentQuery;
import sample.DAO.customerQuery;
import sample.model.appointment;
import sample.model.converter;
import sample.model.customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
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
    public static boolean isMonth = true;
    public static appointment sendAppointment;
    public Button exitButton;
    public DatePicker dateFinder;

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

        dateFinder.setValue(LocalDate.now());
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

    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        int toDelete = 0;
        JDBC.openConnection();
        if(appointmentDisplay.getSelectionModel().getSelectedItem() == null) {
            Alert errorM = new Alert(Alert.AlertType.ERROR);
            errorM.setTitle("No items selected");
            errorM.setHeaderText("Missing items required to delete.");
            errorM.setContentText("Please ensure that you have selected a customer to be deleted.");
            errorM.show();
        }
        else {
            appointment delAppointment = (appointment) appointmentDisplay.getSelectionModel().getSelectedItem();
            toDelete = delAppointment.getAppointmentID();
            String typeDel = delAppointment.getAppointmentType();

            Alert confDel = new Alert(Alert.AlertType.CONFIRMATION);
            confDel.setTitle("Confirm deletion");
            confDel.setHeaderText("Deletion Warning");
            confDel.setContentText("Are you sure you want to delete this appointment?");

            Optional<ButtonType> result = confDel.showAndWait();
            if (result.get() == ButtonType.OK) {
                appointmentQuery.delete(toDelete);
                ObservableList<appointment> listAppointment = FXCollections.observableArrayList();
                listAppointment = appointmentQuery.getAllAppointments();
                appointmentDisplay.setItems(listAppointment);
            }

            Alert didDel = new Alert(Alert.AlertType.INFORMATION);
            didDel.setTitle("Appointment cancelled!");
            didDel.setHeaderText("This appointment was cancelled.");
            didDel.setContentText("The appointment with the ID of " + toDelete + " and the type " + typeDel + " was cancelled.");
            didDel.show();
        }
        JDBC.closeConnection();
    }

    public void viewWeek(ActionEvent actionEvent) {
        isMonth = false;
    }

    public void viewMonth(ActionEvent actionEvent) {
        isMonth = true;
    }

    public void regressView(ActionEvent actionEvent) throws SQLException {
        LocalDate oldDate = dateFinder.getValue();
        LocalDate selectedDate;
        if (isMonth) {
            selectedDate = oldDate.minusDays(30);
        }
        else {
            selectedDate = oldDate.minusDays(7);
        }
        dateFinder.setValue(selectedDate);

        ObservableList<appointment> showList = FXCollections.observableArrayList();
        // check if searching by month or week and obtain list
        JDBC.openConnection();
        if (isMonth) {
            showList = appointmentQuery.getMonthlyAppointments(selectedDate);
        }
        else {
            showList = appointmentQuery.getWeeklyAppointments(selectedDate);
        }
        JDBC.closeConnection();

        appointmentDisplay.setItems(showList);
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

    public void advanceView(ActionEvent actionEvent) throws SQLException {
        LocalDate oldDate = dateFinder.getValue();
        LocalDate selectedDate;
        if (isMonth) {
            selectedDate = oldDate.plusDays(30);
        }
        else {
            selectedDate = oldDate.plusDays(7);
        }
        dateFinder.setValue(selectedDate);
        System.out.println(selectedDate);

        ObservableList<appointment> showList = FXCollections.observableArrayList();
        // check if searching by month or week and obtain list
        JDBC.openConnection();
        if (isMonth) {
            showList = appointmentQuery.getMonthlyAppointments(selectedDate);
        }
        else {
            showList = appointmentQuery.getWeeklyAppointments(selectedDate);
        }
        JDBC.closeConnection();

        appointmentDisplay.setItems(showList);
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

    public void exitWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/menuScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle ("Main Menu");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) appointmentDisplay.getScene().getWindow();
        prevStage.close();
    }

    public void searchForAppointments(ActionEvent actionEvent) throws SQLException {
        // grab date and initialize list
        ObservableList<appointment> showList = FXCollections.observableArrayList();
        LocalDate selectedDate = dateFinder.getValue();
        // check if searching by month or week and obtain list
        JDBC.openConnection();
        if (isMonth) {
            showList = appointmentQuery.getMonthlyAppointments(selectedDate);
        }
        else {
            showList = appointmentQuery.getWeeklyAppointments(selectedDate);
        }
        JDBC.closeConnection();

        appointmentDisplay.setItems(showList);
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
