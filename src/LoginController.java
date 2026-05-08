import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.Guest;
import model.HotelDataBase;
import model.Staff;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            // ✅ VALIDATION FIRST
            if (username.isEmpty() || password.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Please fill all fields!");
                alert.show();
                return;
            }

            HotelDataBase db = HotelDataBase.getInstance();
            Object user = db.validateLogin(username, password);

            if (user != null) {

                Stage stage = (Stage) usernameField.getScene().getWindow();

                // GUEST LOGIN
                if (user instanceof Guest) {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/guestdashboard.fxml")
                    );
                    Parent root = loader.load();

                    GuestDashboardController controller = loader.getController();
                    controller.setGuest((Guest) user);

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(
                            getClass().getResource("/style.css").toExternalForm()
                    );

                    stage.setScene(scene);
                }

                // STAFF LOGIN
                else if (user instanceof Staff) {
                    FXMLLoader loader = new FXMLLoader(
                            getClass().getResource("/staffdashboard.fxml")
                    );
                    Parent root = loader.load();

                    StaffDashboardController controller = loader.getController();
                    controller.setStaff((Staff) user);

                    Scene scene = new Scene(root);
                    scene.getStylesheets().add(
                            getClass().getResource("/style.css").toExternalForm()
                    );

                    stage.setScene(scene);
                }

            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password!");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBack(ActionEvent event) {
        SceneManager.switchScene(event, "welcome.fxml");
    }
}