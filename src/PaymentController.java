import HotelRooms.Invoice;
import HotelRooms.Reservation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    @FXML
    private Label totalAmountLabel;

    @FXML
    private ToggleGroup paymentMethod;

    @FXML
    private TextArea invoiceDisplay;

    @FXML
    private Button confirmButton;

    // NEW FIELD FOR VISA NUMBER
    @FXML
    private TextField visaField;

    private Reservation reservation;

    private double finalPrice = 0.0;

    // RECEIVE DATA
    public void setPaymentData(
            Reservation reservation,
            double price
    ) {

        this.reservation = reservation;
        this.finalPrice = price;

        totalAmountLabel.setText(
                String.format("%.2f EGP", price)
        );
    }

    @FXML
    private void handleCheckout() {

        RadioButton selectedMethod =
                (RadioButton) paymentMethod.getSelectedToggle();

        // CHECK PAYMENT METHOD
        if (selectedMethod == null) {

            showAlert(
                    "Selection Required",
                    "Please select a payment method.",
                    Alert.AlertType.WARNING
            );

            return;
        }

        String method = selectedMethod.getText();

        // VISA VALIDATION
        if (method.equalsIgnoreCase("Visa")) {

            String visaNumber = visaField.getText().trim();

            // MUST BE 16 DIGITS
            if (!visaNumber.matches("\\d{16}")) {

                showAlert(
                        "Invalid Visa",
                        "Visa number must contain exactly 16 digits.",
                        Alert.AlertType.ERROR
                );

                return;
            }
        }

        // BALANCE VALIDATION
        if (method.equalsIgnoreCase("Balance")) {

            if (reservation.getGuest().getBalance()
                    < finalPrice) {

                showAlert(
                        "Error",
                        "Not enough balance!",
                        Alert.AlertType.ERROR
                );

                return;
            }

            reservation.getGuest().setBalance(
                    reservation.getGuest().getBalance()
                            - finalPrice
            );
        }

        // SIMULATE PAYMENT
        boolean success = simulateTransaction();

        if (success) {

            // CREATE INVOICE
            Invoice invoice =
                    new Invoice(finalPrice, true);

            reservation.setInvoice(invoice);

            // GENERATE INVOICE
            generateInvoice(method);

            // SUCCESS MESSAGE
            showAlert(
                    "Success",
                    "Payment Successful!",
                    Alert.AlertType.INFORMATION
            );

            // DISABLE BUTTON
            confirmButton.setDisable(true);

        } else {

            showAlert(
                    "Payment Failed",
                    "Transaction failed.",
                    Alert.AlertType.ERROR
            );
        }
    }

    // BUTTON TO RETURN AFTER READING INVOICE
    @FXML
    private void handleBackToDashboard() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "guestdashboard.fxml"
                            )
                    );

            Parent root = loader.load();

            GuestDashboardController controller =
                    loader.getController();

            controller.setGuest(
                    reservation.getGuest()
            );

            Stage stage =
                    (Stage) confirmButton
                            .getScene()
                            .getWindow();

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            stage.setScene(scene);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void generateInvoice(String method) {

        DateTimeFormatter dtf =
                DateTimeFormatter.ofPattern(
                        "yyyy/MM/dd HH:mm:ss"
                );

        String now =
                dtf.format(LocalDateTime.now());

        String invoiceText =
                "========== HOTEL INVOICE ==========\n"
                        + "Date: " + now + "\n"
                        + "Ref No: "
                        + (int)(Math.random() * 100000)
                        + "\n"
                        + "-----------------------------------\n"
                        + "Guest: "
                        + reservation.getGuest().getName()
                        + "\n"
                        + "Room: "
                        + reservation.getRoom().getRoomNumber()
                        + "\n"
                        + "Check-In: "
                        + reservation.getCheckInDate()
                        + "\n"
                        + "Check-Out: "
                        + reservation.getCheckOutDate()
                        + "\n"
                        + "-----------------------------------\n"
                        + "Total Amount: "
                        + totalAmountLabel.getText()
                        + "\n"
                        + "Payment Method: "
                        + method
                        + "\n"
                        + "Status: COMPLETED\n"
                        + "-----------------------------------\n"
                        + "Thank you for choosing our Hotel!";

        invoiceDisplay.setText(invoiceText);
    }

    private void showAlert(
            String title,
            String message,
            Alert.AlertType type
    ) {

        Alert alert = new Alert(type);

        alert.setTitle(title);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();
    }

    private boolean simulateTransaction() {

        return true;
    }
}