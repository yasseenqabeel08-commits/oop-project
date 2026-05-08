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
import java.util.List;

public class RoomsController {

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> colRoomNumber;

    @FXML
    private TableColumn<Room, String> colPrice;

    @FXML
    private TableColumn<Room, String> colType;

    @FXML
    private TextField priceField;

    @FXML
    private TextField typeField;

    @FXML
    private DatePicker checkInPicker;

    @FXML
    private DatePicker checkOutPicker;

    @FXML
    private Label welcomeLabel;

    private Guest currentGuest;
    @FXML private CheckBox wifiCheck;
    @FXML private CheckBox tvCheck;
    @FXML private CheckBox minibarCheck;
    @FXML private CheckBox poolCheck;
    @FXML private CheckBox gymCheck;
    @FXML private CheckBox jacuzziCheck;
    private HotelDataBase db = HotelDataBase.getInstance();

    @FXML
    public void initialize() {

        colRoomNumber.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRoomNumber()
                )
        );

        colPrice.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(data.getValue().getPricePerNight())
                )
        );

        colType.setCellValueFactory(data ->
                new SimpleStringProperty(
                        data.getValue().getRoomtype().getTypeName()
                )
        );

        roomTable.setItems(
                FXCollections.observableArrayList(db.getRooms())
        );
        loadRooms(); // initial load

        wifiCheck.setOnAction(e -> loadRooms());
        tvCheck.setOnAction(e -> loadRooms());
        minibarCheck.setOnAction(e -> loadRooms());
        poolCheck.setOnAction(e -> loadRooms());
        gymCheck.setOnAction(e -> loadRooms());
        jacuzziCheck.setOnAction(e -> loadRooms());
    }

    public void setGuest(Guest guest) {

        this.currentGuest = guest;

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome " + guest.getName());
        }
    }

    @FXML
    private void handleBack() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("guestdashboard.fxml")
            );

            Parent root = loader.load();

            GuestDashboardController controller =
                    loader.getController();

            controller.setGuest(currentGuest);

            Stage stage =
                    (Stage) roomTable.getScene().getWindow();

            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleFilter() {

        ObservableList<Room> filtered =
                FXCollections.observableArrayList();

        String typeInput =
                typeField.getText().trim().toLowerCase();

        String priceText =
                priceField.getText().trim();

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

            boolean matchesPrice =
                    r.getPricePerNight() <= maxPrice;

            boolean matchesType = true;

            if (!typeInput.isEmpty()) {

                matchesType = r.getRoomtype()
                        .getTypeName()
                        .toLowerCase()
                        .contains(typeInput);
            }

            if (matchesPrice && matchesType) {
                filtered.add(r);
            }
        }

        roomTable.setItems(filtered);
    }

    private void showAlert(String title, String msg) {

        Alert alert =
                new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle(title);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    @FXML
    private void handleReserve() {

        // GET SELECTED ROOM
        Room selectedRoom =
                roomTable.getSelectionModel().getSelectedItem();

        if (selectedRoom == null) {

            showAlert("Error", "Please select a room!");
            return;
        }

        // GET DATES
        LocalDate checkInDate = checkInPicker.getValue();
        LocalDate checkOutDate = checkOutPicker.getValue();

        // VALIDATE DATES
        if (checkInDate == null || checkOutDate == null) {

            showAlert("Error", "Please select both dates!");
            return;
        }

        if (!checkOutDate.isAfter(checkInDate)) {

            showAlert(
                    "Error",
                    "Check-out must be after check-in!"
            );

            return;
        }

        if (currentGuest == null) {

            showAlert("Error", "No user logged in!");
            return;
        }

        // DOUBLE BOOKING CHECK
        for (Reservation r : db.getReservations()) {

            if (r.getRoom().equals(selectedRoom)) {

                if (!(checkOutDate.isBefore(r.getCheckInDate()) ||
                        checkInDate.isAfter(r.getCheckOutDate()))) {

                    showAlert(
                            "Error",
                            "Room already booked for these dates!"
                    );

                    return;
                }
            }
        }

        // CALCULATE COST
        long days =
                java.time.temporal.ChronoUnit.DAYS.between(
                        checkInDate,
                        checkOutDate
                );

        double cost =
                days * selectedRoom.getPricePerNight();

        // CHECK BALANCE
        if (currentGuest.getBalance() < cost) {

            showAlert("Error", "Not enough balance!");
            return;
        }

        try {

            // CREATE RESERVATION
            Reservation reservation = new Reservation(
                    db.getReservations().size() + 1,
                    selectedRoom,
                    currentGuest,
                    checkInDate,
                    checkOutDate
            );

            // SET TOTAL COST
            reservation.setTotalCost(cost);

            // CREATE INVOICE
            Invoice invoice = new Invoice();

            // SET PAYMENT STATUS = PAID
            invoice.setIsPaid(true);

            // ATTACH INVOICE TO RESERVATION
            reservation.setInvoice(invoice);

            // DEDUCT BALANCE
            currentGuest.setBalance(
                    currentGuest.getBalance() - cost
            );

            // ADD RESERVATION
            db.addReservation(reservation);

            showAlert(
                    "Success",
                    "Reservation created successfully!"
            );

        } catch (Exception e) {

            showAlert("Error", e.getMessage());
        }
    }

    @FXML
    private void handleReset() {

        typeField.clear();
        priceField.clear();

        roomTable.setItems(
                FXCollections.observableArrayList(db.getRooms())
        );
    }
    private void loadRooms() {

        List<Room> allRooms = HotelDataBase.getInstance().getRooms();

        // 🔥 GET AMENITIES FROM DATABASE
        List<Amenity> allAmenities = HotelDataBase.getInstance().getAmenities();

        Amenity wifi = allAmenities.stream().filter(a -> a.getName().equals("WiFi")).findFirst().orElse(null);
        Amenity tv = allAmenities.stream().filter(a -> a.getName().equals("TV")).findFirst().orElse(null);
        Amenity minibar = allAmenities.stream().filter(a -> a.getName().equals("Mini-bar")).findFirst().orElse(null);
        Amenity pool = allAmenities.stream().filter(a -> a.getName().equals("Pool")).findFirst().orElse(null);
        Amenity gym = allAmenities.stream().filter(a -> a.getName().equals("Gym")).findFirst().orElse(null);
        Amenity jacuzzi = allAmenities.stream().filter(a -> a.getName().equals("Jacuzzi")).findFirst().orElse(null);

        List<Room> filtered = allRooms.stream().filter(room -> {

            if (wifiCheck.isSelected() && !room.getAmenities().contains(wifi))
                return false;

            if (tvCheck.isSelected() && !room.getAmenities().contains(tv))
                return false;

            if (minibarCheck.isSelected() && !room.getAmenities().contains(minibar))
                return false;

            if (poolCheck.isSelected() && !room.getAmenities().contains(pool))
                return false;

            if (gymCheck.isSelected() && !room.getAmenities().contains(gym))
                return false;

            if (jacuzziCheck.isSelected() && !room.getAmenities().contains(jacuzzi))
                return false;

            return true;

        }).toList();

        roomTable.getItems().setAll(filtered);
    }
}