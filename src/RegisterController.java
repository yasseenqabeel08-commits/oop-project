import HotelRooms.RoomType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.*;
import model.enums.Gender;

import java.time.LocalDate;
import java.time.Period;

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

        // Check empty fields
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
                || address.isEmpty() || genderValue == null
                || dobPicker.getValue() == null || balanceField.getText().isEmpty()) {

            showAlert(Alert.AlertType.ERROR, "Error", "All fields must be filled!");
            return;
        }

        // Check age
        int age = Period.between(dobPicker.getValue(), LocalDate.now()).getYears();

        if (age < 18) {
            showAlert(Alert.AlertType.ERROR, "Error", "You must be at least 18 years old to register!");
            return;
        }

        // Check passwords match
        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Error", "Passwords do not match!");
            return;
        }

        // Check password length
        if (password.length() < 8) {
            showAlert(Alert.AlertType.ERROR, "Error", "Password must be at least 8 characters!");
            return;
        }

        // Validate balance
        double balance;

        try {
            balance = Double.parseDouble(balanceField.getText());

            if (balance < 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Balance cannot be negative!");
                return;
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Balance must be a number!");
            return;
        }

        // Check duplicate username
        if (db.findGuestByUsername(username) != null) {
            showAlert(Alert.AlertType.ERROR, "Error", "Username already exists!");
            return;
        }

        // Default room preferences
        RoomType defaultType = db.getRoomTypes().get(0);

        RoomPrefrences preferences = new RoomPrefrences(
                defaultType,
                1,
                false
        );

        // Create guest
        Guest guest = new Guest(
                username,
                password,
                username,
                dobPicker.getValue(),
                Gender.valueOf(genderValue),
                balance,
                address,
                preferences
        );

        // Add guest to database
        db.getGuests().add(guest);

        showAlert(Alert.AlertType.INFORMATION, "Success", "Registration successful!");

        // Switch to login screen
        SceneManager.switchScene(event, "login.fxml");

        clearFields();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {

        Alert alert = new Alert(type);

        alert.setTitle(title);
        alert.setHeaderText(null);
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