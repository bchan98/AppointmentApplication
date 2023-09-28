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
    public Button customerSel;
    public Button appSel;
    public Button reportButton;

    /** This method sends the user to the appointmentController. Sends the user to the screen where appointments can be viewed and/or modified.
     *
     * @param actionEvent Triggers upon pressing the appSel button.
     * @throws IOException
     */
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

    /** This method sends the user to the customerController. Sends the user to the screen where customers can be viewed and/or modified.
     *
     * @param actionEvent Triggers upon pressing the cusSel button.
     * @throws IOException
     */
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
    /** This method initializes the window. This checks the current time to determine if any incoming appointments are near to alert the user.
     *
     */
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

        /** This lambda expression handles logging out of the main menu. This is a simple JavaFX method that can be quickly called to return the user to the main login screen.
         *
         */
        logoutButton.setOnAction(event -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/sample/view/loginScreen.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 600);
            stage.setTitle ("Login");
            stage.setScene(scene);
            stage.show();

            Stage prevStage = (Stage) logoutButton.getScene().getWindow();
            prevStage.close();
        });
    }

    /** This method sends the user to the reportController. Sends the user to the screen where various reports can be viewed.
     *
     * @param actionEvent Triggers upon pressing the reportButton.
     * @throws IOException
     */
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
}
