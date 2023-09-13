package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
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

    public void saveChanges(ActionEvent actionEvent) {
    }

    public void exitWindow(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

}
