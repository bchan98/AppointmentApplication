package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentQuery;
import sample.model.appointment;
import sample.model.converter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class menuController implements Initializable {
    public Button logoutButton;

    public void sendToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Appointments");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();
    }

    public void sendToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Customers");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LocalDateTime now = LocalDateTime.now();

        try {
            JDBC.openConnection();
            appointment thisAppointment = appointmentQuery.checkRecent(now);
            JDBC.closeConnection();
            int checkValid = thisAppointment.getAppointmentID();
            System.out.println(checkValid);
            if (checkValid != 0) {
                int displayID = thisAppointment.getAppointmentID();
                Timestamp start = thisAppointment.getAppointmentStart();
                LocalDateTime startDateTime = converter.toUserTime(start);

                Alert incomingW = new Alert(Alert.AlertType.WARNING);
                incomingW.setTitle("Incoming appointment!");
                incomingW.setHeaderText("You have an appointment coming up!");
                incomingW.setContentText("You have an appointment coming up with appointment ID " + displayID + " starting at " + startDateTime + ".");
                incomingW.show();

                Stage stage = (Stage) incomingW.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
                stage.toFront();
            }
            else {
                Alert incomingW = new Alert(Alert.AlertType.WARNING);
                incomingW.setTitle("No incoming appointments!");
                incomingW.setHeaderText("You have no incoming appointments!");
                incomingW.setContentText("No incoming appointments within the next 15 minutes were found. Please check to see if you have any upcoming appointments past that in the appointments page.");
                incomingW.show();

                Stage stage = (Stage) incomingW.getDialogPane().getScene().getWindow();
                stage.setAlwaysOnTop(true);
                stage.toFront();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void viewReports(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/reportScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 800, 450);
        stage.setTitle ("Login");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();
    }

    public void logoutUser(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/loginScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 300, 275);
        stage.setTitle ("Login");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) logoutButton.getScene().getWindow();
        prevStage.close();
    }
}
