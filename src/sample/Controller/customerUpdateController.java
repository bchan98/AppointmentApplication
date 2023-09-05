package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class customerUpdateController implements Initializable {
    public TextField nameField;
    public TextField addressField;
    public TextField posCodeField;
    public TextField phoneField;
    public ComboBox countryMenu;
    public ComboBox provinceMenu;

    public void countrySel(ActionEvent actionEvent) {
    }

    public void provinceSel(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
