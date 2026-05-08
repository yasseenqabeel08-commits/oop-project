import HotelRooms.Invoice;
import HotelRooms.Reservation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    @FXML private Label totalAmountLabel;
    @FXML private ToggleGroup paymentMethod;
    @FXML private TextArea invoiceDisplay;
    @FXML private Button confirmButton;
    private Reservation reservation;

    private double finalPrice = 0.0;

    /**
     * This method allows Person 1 or 3 to "send" the total price
     * to your screen during navigation.
     */
    public void setPaymentData(Reservation reservation, double price) {
        this.reservation = reservation;
        this.finalPrice = price;
        totalAmountLabel.setText(String.format("%.2f EGP", price));
    }
    public void setReservation(Reservation reservation, double price) {
        this.reservation = reservation;
        this.finalPrice = price;
        totalAmountLabel.setText(String.format("%.2f EGP", price));
    }
    @FXML
    private void handleCheckout() {
        RadioButton selectedMethod = (RadioButton) paymentMethod.getSelectedToggle();

        // Check if user actually picked a method
        if (selectedMethod == null) {
            showAlert("Selection Required", "Please select a payment method.", Alert.AlertType.WARNING);
            return;
        }

        // Feature: Payment confirmation logic
        boolean success = simulateTransaction();

        if (success) {
            showAlert("Success", "Payment of " + totalAmountLabel.getText() + " confirmed!", Alert.AlertType.INFORMATION);

            // ✅ CREATE INVOICE
            Invoice invoice = new Invoice(finalPrice, true);

            // ✅ LINK TO RESERVATION
            if (reservation != null) {
                reservation.setInvoice(invoice);
            }

            generateInvoice(selectedMethod.getText());
            confirmButton.setDisable(true);
        }
    }

    private void generateInvoice(String method) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String now = dtf.format(LocalDateTime.now());

        String sb = "========== HOTEL INVOICE ==========\n" +
                "Date: " + now + "\n" +
                "Ref No: " + (int) (Math.random() * 100000) + "\n" +
                "-----------------------------------\n" +
                "Total Amount: " + totalAmountLabel.getText() + "\n" +
                "Payment Method: " + method + "\n" +
                "Status: COMPLETED\n" +
                "-----------------------------------\n" +
                "Thank you for choosing our Hotel!";

        invoiceDisplay.setText(sb);
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean simulateTransaction() {
        // Logic for success/failure
        return true;
    }
}