import HotelRooms.RoomType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.*;
import model.enums.Gender;

public class RegisterController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dobPicker;
    @FXML private ComboBox<String> genderBox;
    @FXML private Label errorLabel;
    @FXML private TextField addressField;
    @FXML private TextField balanceField;

    private HotelDataBase db = HotelDataBase.getInstance();

    @FXML
    public void initialize() {
        genderBox.getItems().addAll("MALE", "FEMALE");
    }

    @FXML
    private void handleRegister(ActionEvent event) {

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String address = addressField.getText().trim();
        String genderValue = genderBox.getValue();


        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || address.isEmpty() || genderValue == null
                || dobPicker.getValue() == null || balanceField.getText().isEmpty()) {

            showAlert("Error", "All fields must be filled!");
            return;
        }


        if (!password.equals(confirmPassword)) {
            showAlert("Error", "Passwords do not match!");
            return;
        }


        if (password.length() < 6) {
            showAlert("Error", "Password must be at least 6 characters!");
            return;
        }


        double balance;
        try {
            balance = Double.parseDouble(balanceField.getText());
            if (balance < 0) {
                showAlert("Error", "Balance cannot be negative!");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Balance must be a number!");
            return;
        }


        if (db.findGuestByUsername(username) != null) {
            showAlert("Error", "Username already exists!");
            return;
        }


        RoomType defaultType = db.getRoomTypes().get(0); // SINGLE
        RoomPrefrences preferences = new RoomPrefrences(defaultType, 1, false);


        Guest guest = new Guest(
                username,
                password,
                username, // name (you can improve later)
                dobPicker.getValue(),
                Gender.valueOf(genderValue),
                balance,
                address,
                preferences
        );


        db.getGuests().add(guest);

        showAlert("Success", "Registration successful!");


        SceneManager.switchScene(event,"login.fxml");
        clearFields();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        usernameField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        addressField.clear();
        balanceField.clear();
        genderBox.setValue(null);
        dobPicker.setValue(null);
    }


    @FXML
    private void handleBack(ActionEvent event) {
        SceneManager.switchScene(event, "welcome.fxml");
    }

}