import HotelRooms.Reservation;
import HotelRooms.Room;
import javafx.fxml.FXML;
import model.HotelDataBase;
import model.*;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class StaffDashboardController {



        @FXML
        private TextArea displayArea;

        private HotelDataBase db = HotelDataBase.getInstance();
    @FXML
    private Label welcomeLabel;

    private Staff currentStaff;


        @FXML
        private void handleViewRooms() {
            StringBuilder sb = new StringBuilder("Rooms:\n");

            for (Room r : db.getRooms()) {
                sb.append("Room ").append(r.getRoomNumber())
                        .append(" | Price: ").append(r.getPricePerNight())
                        .append("\n");
            }

            displayArea.setText(sb.toString());
        }

        @FXML
        private void handleViewReservations() {
            StringBuilder sb = new StringBuilder("Reservations:\n");

            for (Reservation r : db.getReservations()) {
                sb.append("ID: ").append(r.getReservationId())
                        .append(" | Room: ").append(r.getRoom().getRoomNumber())
                        .append("\n");
            }

            displayArea.setText(sb.toString());
        }

        @FXML
        private void handleCheckIn() {
            displayArea.setText("Check-in feature coming soon...");
        }

        @FXML
        private void handleCheckOut() {
            displayArea.setText("Check-out feature coming soon...");
        }

        @FXML
        private void handleLogout() {
            try {
                Stage stage = (Stage) displayArea.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/login.fxml")
                );
                Parent root = loader.load();
                Scene scene = new Scene(root);
                scene.getStylesheets().add(
                        getClass().getResource("/style.css").toExternalForm()
                );
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void setStaff(Staff staff) {
        this.currentStaff = staff;
        welcomeLabel.setText("Welcome " + staff.getName());
    }
    }

