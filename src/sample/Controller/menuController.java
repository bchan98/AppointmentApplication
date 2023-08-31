package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {
    public void sendToAppointments(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Appointments");
        stage.setScene(scene);
        stage.show();
    }

    public void sendToCustomers(ActionEvent actionEvent) {
    }
}
