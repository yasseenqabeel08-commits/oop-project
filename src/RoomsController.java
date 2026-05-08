
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

    import java.time.LocalDate;

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
        @FXML private DatePicker checkInPicker;
        @FXML private DatePicker checkOutPicker;

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
                            data.getValue().getRoomtype().getTypeName()
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

            String typeInput = typeField.getText().trim().toLowerCase();
            String priceText = priceField.getText().trim();

            double maxPrice = Double.MAX_VALUE;

            //  parse price safely
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

                boolean matchesType = true; // default = accept all

                if (!typeInput.isEmpty()) {
                    matchesType = r.getRoomtype()
                            .getTypeName()
                            .toLowerCase()
                            .contains(typeInput); // 🔥 better than equals
                }

                //  IMPORTANT LINE
                if (matchesPrice && matchesType) {
                    filtered.add(r);
                }
            }

            roomTable.setItems(filtered);
        }
        private void showAlert(String title, String msg) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setContentText(msg);
            alert.showAndWait();
        }
        @FXML
        private void handleReserve() {

            // 1. Get selected room
            Room selectedRoom = roomTable.getSelectionModel().getSelectedItem();

            if (selectedRoom == null) {
                showAlert("Error", "Please select a room!");
                return;
            }

            // 2. Get dates from DatePickers
            LocalDate checkInDate = checkInPicker.getValue();
            LocalDate checkOutDate = checkOutPicker.getValue();

            // 3. Validate null
            if (checkInDate == null || checkOutDate == null) {
                showAlert("Error", "Please select both dates!");
                return;
            }

            // 4. Validate logic
            if (!checkOutDate.isAfter(checkInDate)) {
                showAlert("Error", "Check-out must be after check-in!");
                return;
            }

            if (currentGuest == null) {
                showAlert("Error", "No user logged in!");
                return;
            }

            // ✅ NEW: DOUBLE BOOKING CHECK
            for (Reservation r : db.getReservations()) {
                if (r.getRoom().equals(selectedRoom)) {

                    if (!(checkOutDate.isBefore(r.getCheckInDate()) ||
                            checkInDate.isAfter(r.getCheckOutDate()))) {

                        showAlert("Error", "Room already booked for these dates!");
                        return;
                    }
                }
            }

            // ✅ NEW: CALCULATE COST
            long days = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
            double cost = days * selectedRoom.getPricePerNight();

            // ✅ NEW: CHECK BALANCE
            if (currentGuest.getBalance() < cost) {
                showAlert("Error", "Not enough balance!");
                return;
            }

            try {

                // 5. Create reservation (YOUR ORIGINAL)
                Reservation reservation = new Reservation(
                        db.getReservations().size() + 1,
                        selectedRoom,
                        currentGuest,
                        checkInDate,
                        checkOutDate
                );

                // ✅ NEW: SET COST (only if your class has setter)
                reservation.setTotalCost(cost);

                // ✅ NEW: DEDUCT BALANCE
                currentGuest.setBalance(currentGuest.getBalance() - cost);

                db.addReservation(reservation);

                showAlert("Success", "Reservation created successfully!");

            } catch (Exception e) {
                showAlert("Error", e.getMessage());
            }
        }
        @FXML
        private void handleReset() {

            // clear filters
            typeField.clear();
            priceField.clear();

            // reload all rooms
            roomTable.setItems(FXCollections.observableArrayList(db.getRooms()));
        }
    }

