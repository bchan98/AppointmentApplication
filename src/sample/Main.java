package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.userQuery;
import sample.model.converter;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/loginScreen.fxml"));
        primaryStage.setTitle("Appointment Generator");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
