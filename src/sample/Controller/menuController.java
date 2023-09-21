package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentQuery;
import sample.model.appointment;
import sample.model.converter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;

public class menuController implements Initializable {
    public void sendToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void sendToCustomers(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/customerScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Customers");
        stage.setScene(scene);
        stage.show();
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
                System.out.println("You have an incoming appointment!");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
