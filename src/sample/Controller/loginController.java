package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.userQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController{

    public TextField userField;
    public PasswordField passField;
    public Label authError;

    public void checkPass(ActionEvent actionEvent) throws SQLException, IOException {
        String usr = userField.getText();
        String pass = passField.getText();

        JDBC.openConnection();
        boolean success = userQuery.verifyCredentials(usr, pass);
        if(success == true)
        {
            System.out.println("Success!");
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 400);
            stage.setTitle ("Appointments");
            stage.setScene(scene);
            stage.show();
        }
        else {
            authError.setText("Either the username or password are incorrect. Please try again!");
        }
        JDBC.closeConnection();
    }
}
