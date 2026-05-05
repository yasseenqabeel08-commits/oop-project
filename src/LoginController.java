import javafx.fxml.FXML;
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

            HotelDataBase db = HotelDataBase.getInstance();
            Object user = db.validateLogin(username, password);

            if (user != null) {

                Stage stage = (Stage) usernameField.getScene().getWindow();

                if (user instanceof Guest) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("guestdashboard.fxml"));
                    Parent root = loader.load();
                    stage.setScene(new Scene(root));
                } else if (user instanceof Staff) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("staffdashboard.fxml"));
                    Parent root = loader.load();
                    stage.setScene(new Scene(root));
                }

            } else {
                System.out.println("Invalid credentials");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}