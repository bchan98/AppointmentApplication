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
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

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


    private static ObservableList<String> allTimes;
    private static ObservableList<String> allContacts;
    private static ObservableList<String> allCustomerNames;

    /** This method generates a list of times in fifteen minute intervals. Runs a loop with all times to be put into the time selection portion of the list.
     *
     * @return Returns an ObservableList allTimes to be put into the combobox timeStart and timeEnd.
     */
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

    /** This method saves changes made to an appointment. Checks if the appointment is created/modified and goes through several logic checks. before calling the appointmentQuery class.
     *
     * @param actionEvent Triggers upon pressing saveButton.
     * @throws SQLException
     * @throws IOException
     */
    public void saveChanges(ActionEvent actionEvent) throws SQLException, IOException {
        // initialize variables
        boolean checkFlag = true;
        int nuCustomerID = 0;
        int nuUserID = 0;
        int nuContactID = 0;
        Timestamp createTime = null;
        String createUser = null;
        Timestamp nuStart = null;
        Timestamp nuEnd = null;
        boolean noOverlapFlag = true;

        // get raw data and check input is valid
        int nuAID = 0;
        nuAID = Integer.parseInt(IDField.getText());
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
            nuCustomerID = converter.toCustomerID(nuCustomer);
            nuContactID = converter.toContactID(nuContact);
            nuUserID = converter.toUserID(loginController.loggedUser);
            JDBC.closeConnection();

            System.out.println(nuCustomerID);
            System.out.println(nuContactID);
            System.out.println(nuUserID);
        }

        // get creation users/date and last update users/date

        if(appointmentController.isAdd) {
            createTime = new Timestamp(System.currentTimeMillis());
            createUser = loginController.loggedUser;
        }
        else {
            createTime = appointmentController.sendAppointment.getAppointmentCreationDate();
            createUser = appointmentController.sendAppointment.getAppointmentCreationUser();
        }

        Timestamp lastTime = new Timestamp(System.currentTimeMillis());
        String lastUser = loginController.loggedUser;

        // convert time to MySQL compatible format
        if (startField.getValue() == null || endField.getValue() == null || timeStart.getValue() == null || timeEnd.getValue() == null) {
            checkFlag = false;
        }
        else {
            // gathers current data on recorded time and date
            LocalDate startDate = startField.getValue();
            String startTime = (String) timeStart.getValue();
            LocalDate endDate = endField.getValue();
            String endTime = (String) timeEnd.getValue();

            // converts recorded time strings to actual time objects
            LocalTime time1 = LocalTime.parse(startTime);
            LocalTime time2 = LocalTime.parse(endTime);

            // merges strings and date into one LocalDateTimeObject
            LocalDateTime nuStart1 = LocalDateTime.of(startDate, time1);
            LocalDateTime nuEnd1 = LocalDateTime.of(endDate, time2);

            // converts LocalDateTime into a Timestamp with a conversion to UTC.
            nuStart = converter.toUniversalTime(nuStart1);
            nuEnd = converter.toUniversalTime(nuEnd1);
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
            appointment nuAppointment = new appointment(nuAID, nuTitle, nuDesc, nuLoc, nuType, nuStart, nuEnd, createTime, createUser, lastTime, lastUser, nuCustomerID, nuUserID, nuContactID);
            // make flags for last checks over timing
            boolean checkHours = appointmentQuery.checkCompanyTime(nuAppointment);
            JDBC.openConnection();
            noOverlapFlag = appointmentQuery.checkOverlap(nuAppointment);
            JDBC.closeConnection();

            if(noOverlapFlag == false) {
                Alert errorW = new Alert(Alert.AlertType.ERROR);
                errorW.setTitle("Overlap in appointment times!");
                errorW.setHeaderText("Overlap in appointment time detected!");
                errorW.setContentText("Your appointment currently has an overlap with another appointment time. Please adjust this or conflicting appointment times.");
                errorW.show();
            }
            else if(checkHours == false) {
                Alert errorW = new Alert(Alert.AlertType.ERROR);
                errorW.setTitle("Outside company hours!");
                errorW.setHeaderText("This appointment lies outside company hours.");
                errorW.setContentText("This appointment lies outside company hours. Please adjust the start and end times so they fall between company hours of 8:00 and 22:00 EST.");
                errorW.show();
            }
            else {
                if(appointmentController.isAdd) {
                    System.out.println(nuAppointment.getCustomerID());
                    System.out.println(nuAppointment.getUserID());
                    System.out.println(nuAppointment.getContactID());

                    JDBC.openConnection();
                    appointmentQuery.create(nuAppointment);
                    JDBC.closeConnection();
                }
                else {
                    JDBC.openConnection();
                    appointmentQuery.update(nuAppointment);
                    JDBC.closeConnection();
                }

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
    }

    /** This method sends the user back to the appointmentScreen. Cancels any changes and returns the user back to the original screen.
     *
     * @param actionEvent Triggers upon pressing the exitButton.
     * @throws IOException
     */
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

    /** This method initializes the window. Checks to determine if any information is passed from the original window and fills in the appropriate fields.
     *
     */
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
            // get strings to put in text fields
            IDField.setText(String.valueOf(appointmentController.sendAppointment.getAppointmentID()));
            titleField.setText(appointmentController.sendAppointment.getTitle());
            descField.setText(appointmentController.sendAppointment.getDescription());
            locField.setText(appointmentController.sendAppointment.getLocation());
            typeField.setText(appointmentController.sendAppointment.getAppointmentType());

            // get timestamp, convert to LocalDateTime and separate into two objects to put into required fields.
            LocalDateTime tempStart = converter.toUserTime(appointmentController.sendAppointment.getAppointmentStart());
            LocalDateTime tempEnd = converter.toUserTime(appointmentController.sendAppointment.getAppointmentEnd());

            LocalDate tempStartDate = tempStart.toLocalDate();
            LocalTime tempStartTime = tempStart.toLocalTime();
            LocalDate tempEndDate = tempEnd.toLocalDate();
            LocalTime tempEndTime = tempEnd.toLocalTime();

            // specify format for time string and convert time to string. put time into object.

            DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
            String sendStartTime = tempStartTime.format(timeFormat);
            String sendEndTime = tempEndTime.format(timeFormat);

            System.out.println(TimeZone.getDefault());

            timeStart.setValue(sendStartTime);
            timeEnd.setValue(sendEndTime);
            startField.setValue(tempStartDate);
            endField.setValue(tempEndDate);

            JDBC.openConnection();
            try {
                String contactName = converter.toContactName(appointmentController.sendAppointment.getContactID());
                String customerName = converter.toCustomerName(appointmentController.sendAppointment.getCustomerID());
                contactBox.setValue(contactName);
                customerBox.setValue(customerName);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            JDBC.closeConnection();
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

    /** This finds the ID of the last appointment existing. Checks the MySQL database for existing appointments.
     *
     * @return Returns the appointment ID of the last appointment made.
     * @throws SQLException
     */
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
