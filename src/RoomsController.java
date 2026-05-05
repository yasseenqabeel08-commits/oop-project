
    import javafx.fxml.FXML;
    import javafx.scene.control.Label;
    import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import model.*;
import HotelRooms.*;

    public class RoomsController {

        @FXML
        private TableView<Room> roomTable;

        @FXML
        private TableColumn<Room, String> colRoomNumber;

        @FXML
        private TableColumn<Room, String> colPrice;

        private HotelDataBase db = HotelDataBase.getInstance();

        @FXML
        public void initialize() {
            colRoomNumber.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            data.getValue().getRoomNumber()
                    )
            );

            colPrice.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            String.valueOf(data.getValue().getPricePerNight())
                    )
            );

            roomTable.setItems(FXCollections.observableArrayList(db.getRooms()));
        }

        private Guest currentGuest;

        @FXML
        private Label welcomeLabel;

        public void setGuest(Guest guest) {
            this.currentGuest = guest;

            if (welcomeLabel != null) {
                welcomeLabel.setText("Welcome " + guest.getName());
            }
        }

        @FXML
        private void handleReserve() {
            Room selected = roomTable.getSelectionModel().getSelectedItem();

            if (selected == null) {
                System.out.println("No room selected");
                return;
            }

            System.out.println("Selected room: " + selected.getRoomNumber());

            // later: send to reservation system (Person 1)
        }

        @FXML
        private void handleBack() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("guestdashboard.fxml"));
                Parent root = loader.load();

                // pass guest back
                GuestDashboardController controller = loader.getController();
                controller.setGuest(currentGuest);

                Stage stage = (Stage) roomTable.getScene().getWindow();
                stage.setScene(new Scene(root));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

