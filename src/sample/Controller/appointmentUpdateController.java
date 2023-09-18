package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.appointmentQuery;
import sample.model.appointment;
import sample.model.converter;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class appointmentUpdateController implements Initializable {
    public TextField IDField;
    public TextField titleField;
    public TextField descField;
    public TextField locField;
    public TextField typeField;
    public DatePicker startField;
    public DatePicker endField;
    public ComboBox customerBox;
    public ComboBox contactBox;
    public Button saveButton;
    public Button exitButton;
    public ComboBox timeStart;
    public ComboBox timeEnd;
    public static Timestamp createTime;
    public static String createUser;
    public static Timestamp nuStart;
    public static Timestamp nuEnd;
    public static int nuCustomerID;
    public static int nuUserID;
    public static int nuContactID;

    private static ObservableList<String> allTimes;
    private static ObservableList<String> allContacts;
    private static ObservableList<String> allCustomerNames;

    public ObservableList<String> generateTime() {
        ObservableList<String> nuTimes = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime startT = LocalTime.of(00, 00);
        int counter = 0;
        while(counter < 96) {
            String nuTime = startT.format(formatter);
            nuTimes.add(nuTime);
            startT = startT.plusMinutes(15);
            counter++;
        }

        return nuTimes;
    }


    public void saveChanges(ActionEvent actionEvent) throws SQLException, IOException {
        boolean checkFlag = true;

        // get raw data and check input is valid
        int nuAID = 0;
        nuAID = Integer.getInteger(IDField.getText());
        String nuTitle = titleField.getText();
        String nuDesc = descField.getText();
        String nuLoc = locField.getText();
        String nuType = typeField.getText();

        if(nuTitle.trim().isEmpty() || nuDesc.trim().isEmpty() || nuLoc.trim().isEmpty() || nuType.trim().isEmpty()) {
            checkFlag = false;
        }

        if(customerBox.getValue() == null || contactBox.getValue() == null ) {
            checkFlag = false;
        }
        else {
            String nuCustomer = (String) customerBox.getValue();
            String nuContact = (String) contactBox.getValue();

            // get user/customer/contact ID;
            JDBC.openConnection();
            int nuCustomerID = converter.toCustomerID(nuCustomer);
            int nuContactID = converter.toContactID(nuContact);
            int nuUserID = converter.toUserID(loginController.loggedUser);
            JDBC.closeConnection();
        }

        // get creation users/date and last update users/date

        if(appointmentController.isAdd) {
            Timestamp createTime = new Timestamp(System.currentTimeMillis());
            String createUser = loginController.loggedUser;
        }
        else {
            Timestamp createTime = appointmentController.sendAppointment.getAppointmentCreationDate();
            String createUser = appointmentController.sendAppointment.getAppointmentCreationUser();
        }

        Timestamp lastTime = new Timestamp(System.currentTimeMillis());
        String lastUser = loginController.loggedUser;

        // convert time to MySQL compatible format
        if (startField.getValue() == null || endField.getValue() == null || timeStart.getValue() == null || timeEnd.getValue() == null) {
            checkFlag = false;
        }
        else {
            LocalDate startDate = startField.getValue();
            String startTime = (String) timeStart.getValue();
            LocalDate endDate = endField.getValue();
            String endTime = (String) timeEnd.getValue();

            LocalTime time1 = LocalTime.parse(startTime);
            LocalTime time2 = LocalTime.parse(endTime);

            LocalDateTime nuStart1 = LocalDateTime.of(startDate, time1);
            LocalDateTime nuEnd1 = LocalDateTime.of(endDate, time2);

            Timestamp nuStart = converter.toUniversalTime(nuStart1);
            Timestamp nuEnd = converter.toUniversalTime(nuEnd1);
        }

        // make an appointment object and save to MySQL
        if(checkFlag == false) {
            Alert errorM = new Alert(Alert.AlertType.ERROR);
            errorM.setTitle("Missing parameters");
            errorM.setHeaderText("Some parameters have not been met.");
            errorM.setContentText("Please check to see if all fields have been properly inputted with information.");
            errorM.show();
        }
        else {
            JDBC.openConnection();
            appointment nuAppointment = new appointment(nuAID, nuTitle, nuDesc, nuLoc, nuType, nuStart, nuEnd, createTime, createUser, lastTime, lastUser, nuCustomerID, nuUserID, nuContactID);
            appointmentQuery.create(nuAppointment);
            JDBC.closeConnection();

            // return to old window and close previous
            Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 1000, 400);
            stage.setTitle ("Appointments");
            stage.setScene(scene);
            stage.show();

            Stage curStage = (Stage) saveButton.getScene().getWindow();
            curStage.close();
        }
    }


    public void exitWindow(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/sample/view/appointmentScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 400);
        stage.setTitle ("Appointments");
        stage.setScene(scene);
        stage.show();

        Stage curStage = (Stage) saveButton.getScene().getWindow();
        curStage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // get all customers + all contacts to populate combo boxes
        JDBC.openConnection();
        try {
            allContacts = appointmentQuery.getAllContacts();
            contactBox.setItems(allContacts);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            allCustomerNames = appointmentQuery.getAllCustomerNames();
            customerBox.setItems(allCustomerNames);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        JDBC.closeConnection();

        //get times for time selection combobox
        allTimes = generateTime();
        timeStart.setItems(allTimes);
        timeEnd.setItems(allTimes);


        // check if modify - if modify, fill in values
        if(!appointmentController.isAdd) {
            //get strings to put in text fields
            IDField.setText(String.valueOf(appointmentController.sendAppointment.getAppointmentID()));
            titleField.setText(appointmentController.sendAppointment.getTitle());
            descField.setText(appointmentController.sendAppointment.getDescription());
            locField.setText(appointmentController.sendAppointment.getLocation());
            typeField.setText(appointmentController.sendAppointment.getLocation());
        }
        // if create, get new appointmentID
        else {
            JDBC.openConnection();
            try {
                IDField.setText(String.valueOf(getLastAID() + 1));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBC.closeConnection();
        }
    }

    public int getLastAID() throws SQLException {
        int counter = 0;
        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            counter = rs.getInt("Appointment_ID");
        }
        return counter;
    }
}
