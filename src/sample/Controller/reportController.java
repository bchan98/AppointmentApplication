package sample.Controller;

import com.mysql.cj.xdevapi.Table;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.model.appointment;
import sample.model.reporter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class reportController implements Initializable {
    public TableView reportTable;

    public void viewAllAppointments(ActionEvent actionEvent) throws SQLException {
        reportTable.getColumns().clear();

        ObservableList<String> listReport = FXCollections.observableArrayList();
        JDBC.openConnection();
        listReport = reporter.getAppointmentReport();
        JDBC.closeConnection();

        TableColumn<String, String> totalCol = new TableColumn("Information");
        totalCol.setCellValueFactory(data -> {
            String value = data.getValue();
            return new SimpleStringProperty(value);
        });

        reportTable.setItems(listReport);
        reportTable.getColumns().add(totalCol);
    }

    public void viewContactAppointments(ActionEvent actionEvent) throws SQLException {
        // clear table
        reportTable.getColumns().clear();

        ObservableList<appointment> listAppointment = FXCollections.observableArrayList();
        JDBC.openConnection();
        listAppointment = reporter.getContactAppointments();
        JDBC.closeConnection();

        reportTable.setItems(listAppointment);
        TableColumn appContCol = new TableColumn("Contact ID");
        TableColumn appAIDCol = new TableColumn("Appointment ID");
        TableColumn appTitleCol = new TableColumn("Title");
        TableColumn appTypeCol = new TableColumn("Type");
        TableColumn appDesCol = new TableColumn("Description");
        TableColumn appStartCol = new TableColumn("Start");
        TableColumn appEndCol = new TableColumn("End");
        TableColumn appCIDCol = new TableColumn("Customer ID");
        reportTable.getColumns().addAll(appContCol, appAIDCol, appTitleCol, appTypeCol, appDesCol, appStartCol, appEndCol, appCIDCol);

        appContCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        appAIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointmentType"));
        appDesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("appointmentStart"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("appointmentEnd"));
        appCIDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
    }

    public void viewCreation(ActionEvent actionEvent) throws SQLException {
        reportTable.getColumns().clear();

        ObservableList<String> listCreation = FXCollections.observableArrayList();
        JDBC.openConnection();
        listCreation = reporter.getCreationActivity();
        JDBC.closeConnection();

        TableColumn<String, String> totalCol = new TableColumn("Information");
        totalCol.setCellValueFactory(data -> {
            String value = data.getValue();
            return new SimpleStringProperty(value);
        });

        reportTable.setItems(listCreation);
        reportTable.getColumns().add(totalCol);
    }

    public void returnMenu(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/menuScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 400, 400);
        stage.setTitle ("Main Menu");
        stage.setScene(scene);
        stage.show();

        Stage prevStage = (Stage) reportTable.getScene().getWindow();
        prevStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
