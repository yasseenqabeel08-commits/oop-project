
    import javafx.beans.property.SimpleStringProperty;
    import javafx.collections.ObservableList;
    import javafx.fxml.FXML;
    import javafx.scene.control.*;
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
        @FXML
        private TextField priceField;

        @FXML
        private TextField typeField;
        private HotelDataBase db = HotelDataBase.getInstance();
        @FXML
        private TableColumn<Room, String> colType;

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

            colType.setCellValueFactory(data ->
                    new javafx.beans.property.SimpleStringProperty(
                            data.getValue().getRoomtype().toString()
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
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please select a room first!");
                alert.show();
                return;
            }

            // ✅ TEMP simulation
            System.out.println("Reserved room: " + selected.getRoomNumber());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Room reserved successfully (simulation)");
            alert.show();
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
        @FXML
        private void handleFilter() {

            ObservableList<Room> filtered = FXCollections.observableArrayList();

            String typeInput = typeField.getText().trim().toUpperCase();
            String priceText = priceField.getText().trim();
            boolean matchesType = true;
            double maxPrice = Double.MAX_VALUE;

            try {
                if (!priceText.isEmpty()) {
                    maxPrice = Double.parseDouble(priceText);
                }
            } catch (Exception e) {
                System.out.println("Invalid price");
                return;
            }

            for (Room r : db.getRooms()) {

                boolean matchesPrice = r.getPricePerNight() <= maxPrice;


                if (!typeInput.isEmpty()) {
                    try {
                        matchesType = r.getRoomtype().getTypeName().equalsIgnoreCase(typeInput);
                    } catch (Exception e) {
                        System.out.println("Invalid type");
                        return;

                }

                    if (!typeInput.isEmpty()) {
                        matchesType = r.getRoomtype()
                                .getTypeName()
                                .equalsIgnoreCase(typeInput);
                    }
            }

            roomTable.setItems(filtered);
        }

            roomTable.setItems(filtered);
        }
        @FXML
        private void handleReset() {
            roomTable.setItems(FXCollections.observableArrayList(db.getRooms()));
        }
    }

