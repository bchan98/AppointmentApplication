package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DAO.JDBC;
import sample.DAO.userQuery;
import sample.model.reporter;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class loginController implements Initializable {

    public TextField userField;
    public PasswordField passField;
    public static String loggedUser;
    public Label timeZoneDisplay;
    private static String warningAlert;
    private static String warningMessage;
    private static String userPrompt;
    private static String passPrompt;
    private static String timezoneLabel;

    /** This method authenticates user information. Inputted username and passwords are checked with existing data in the MySQL database, before being either accepted or rejected, with login attempts being logged.
     *
     * @param actionEvent Triggers upon pressing the loginButton
     * @throws SQLException
     * @throws IOException
     */
    public void checkPass(ActionEvent actionEvent) throws SQLException, IOException {
        String usr = userField.getText();
        String pass = passField.getText();

        JDBC.openConnection();
        boolean success = userQuery.verifyCredentials(usr, pass);
        JDBC.closeConnection();
        reporter.loginChecker(usr, pass, success);
        if(success == true)
        {
            loggedUser = usr;

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
            Alert logError = new Alert(Alert.AlertType.WARNING);
            logError.setTitle(warningAlert);
            logError.setHeaderText(warningAlert);
            logError.setContentText(warningMessage);
            logError.show();
        }
    }

    /** This method initializes the window. Checks the system timezone and locale, before changing button text to either English or French when detected.
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale thisLocale = Locale.getDefault();
        String sysLang = thisLocale.getLanguage();
        TimeZone thisZone = TimeZone.getDefault();
        String theZone = thisZone.getID();

        if (sysLang.equals("fr")) {
            warningAlert = "Avertissement!";
            warningMessage = "Vous avez saisi soit un nom d'utilisateur invalide, soit un mot de passe incorrect. Veuillez réessayer.";
            userField.setPromptText("Entrez votre pseudo ici: ");
            passField.setPromptText("Entrez votre mot de passe ici: ");
            String timezoneLabel = "Fuseau horaire: " + theZone;
            timeZoneDisplay.setText(timezoneLabel);
        }
        else {
            warningAlert = "Warning!";
            warningMessage = "You have entered either an invalid username or an incorrect password. Please try again.";
            userField.setPromptText("Enter your username here: ");
            passField.setPromptText("Enter your password here: ");
            String timezoneLabel = "Timezone: " + theZone;
            timeZoneDisplay.setText(timezoneLabel);
        }
    }
}
