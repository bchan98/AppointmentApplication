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
    public static String loggedUser;

    public void checkPass(ActionEvent actionEvent) throws SQLException, IOException {
        String usr = userField.getText();
        String pass = passField.getText();

        JDBC.openConnection();
        boolean success = userQuery.verifyCredentials(usr, pass);
        if(success == true)
        {
            loggedUser = usr;

            System.out.println("Success!");
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/menuScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 400, 400);
            stage.setTitle ("Main Menu");
            stage.setScene(scene);
            stage.show();

            Stage prevStage = (Stage) userField.getScene().getWindow();
            prevStage.close();
        }
        else {
            authError.setText("Either the username or password are incorrect. Please try again!");
        }
        JDBC.closeConnection();
    }
}
