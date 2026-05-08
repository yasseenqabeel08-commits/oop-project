import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PaymentController {

    @FXML private Label totalAmountLabel;
    @FXML private ToggleGroup paymentMethod;
    @FXML private TextArea invoiceDisplay;

    // This is called when Person 1/3 sends the price to this screen
    public void setPaymentData(double price) {
        totalAmountLabel.setText(String.format("%.2f EGP", price));
    }

    @FXML
    private void handleCheckout() {
        RadioButton selectedMethod = (RadioButton) paymentMethod.getSelectedToggle();

        if (selectedMethod == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Please select a payment method!");
            alert.show();
            return;
        }

        String method = selectedMethod.getText();

        // Show Success Message
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Payment Successful");
        success.setContentText("Paid via " + method);
        success.showAndWait();

        // Generate Invoice
        generateInvoice(method);
    }

    private void generateInvoice(String method) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        invoiceDisplay.setText(
                "========== HOTEL INVOICE ==========\n" +
                        "Date: " + dtf.format(LocalDateTime.now()) + "\n" +
                        "Total: " + totalAmountLabel.getText() + "\n" +
                        "Method: " + method + "\n" +
                        "Status: COMPLETED\n" +
                        "==================================="
        );
    }
}