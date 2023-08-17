package sample.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.DAO.JDBC;
import sample.DAO.userQuery;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class loginController{

    public TextField userField;
    public TextField passField;

    public void checkPass(ActionEvent actionEvent) throws SQLException {
        String usr = userField.getText();
        String pass = passField.getText();

        JDBC.openConnection();
        boolean success = userQuery.verifyCredentials(usr, pass);
        if(success == true)
        {
            System.out.println("Success!");
        }
        else {
            System.out.println("Failure!");
        }
        JDBC.closeConnection();
    }
}
