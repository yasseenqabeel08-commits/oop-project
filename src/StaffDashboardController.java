import HotelRooms.Reservation;
import javafx.beans.property.SimpleStringProperty;
import HotelRooms.Room;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.media.MediaPlayer;
import model.HotelDataBase;
import model.*;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import model.enums.ReservationStatus;

public class StaffDashboardController {

        private HotelDataBase db = HotelDataBase.getInstance();
    @FXML
    private Label welcomeLabel;

    private Staff currentStaff;

//    @FXML private TableView<Reservation> reservationTable;

    @FXML private TableColumn<Reservation, String> colId;
    @FXML private TableColumn<Reservation, String> colGuest;
    @FXML private TableColumn<Reservation, String> colRoom;
    @FXML private TableColumn<Reservation, String> colStatus;
    @FXML private TableView<Object> mainTable;

    @FXML private TableColumn<Object, String> col1;
    @FXML private TableColumn<Object, String> col2;
    @FXML private TableColumn<Object, String> col3;
    @FXML private TableColumn<Object, String> col4;
    @FXML
    public void initialize() {

//        colId.setCellValueFactory(data ->
//                new SimpleStringProperty(
//                        String.valueOf(data.getValue().getReservationId())
//                )
//        );
//
//        colGuest.setCellValueFactory(data ->
//                new SimpleStringProperty(
//                        data.getValue().getGuest().getName()
//                )
//        );
//
//        colRoom.setCellValueFactory(data ->
//                new SimpleStringProperty(
//                        data.getValue().getRoom().getRoomNumber()
//                )
//        );
//
//        colStatus.setCellValueFactory(data ->
//                new SimpleStringProperty(
//                        data.getValue().getStatus().toString()
//                )
//        );
    }
    @FXML
    private void handleCheckIn() {

        Object selectedItem = mainTable.getSelectionModel().getSelectedItem();

        // 1️⃣ Nothing selected
        if (selectedItem == null) {
            showAlert("Please select a reservation first!");
            return;
        }

        // 2️⃣ Wrong type (e.g. rooms instead of reservations)
        if (!(selectedItem instanceof Reservation)) {
            showAlert("Please select a reservation (not a room)!");
            return;
        }

        Reservation selected = (Reservation) selectedItem;

        // 3️⃣ Check status
        if (selected.getStatus() == ReservationStatus.PENDING) {

            selected.setStatus(ReservationStatus.CONFIRMED);

            mainTable.refresh();

            showAlert("Guest checked in successfully!");

        } else {
            showAlert("Only PENDING reservations can be checked in!");
        }
    }
    @FXML
    private void handleViewRooms() {

        col1.setText("Room number");
        col2.setText("Type");
        col3.setText("Price");
        col4.setText("Status");

        col1.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(((Room)data.getValue()).getRoomNumber())
                ));

        col2.setCellValueFactory(data ->
                new SimpleStringProperty(
                        ((Room)data.getValue()).getRoomtype().toString()
                ));

        col3.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(((Room)data.getValue()).getPricePerNight())
                ));

        col4.setText("Availability");

        col4.setCellValueFactory(data ->
                new SimpleStringProperty(
                        ((Room)data.getValue()).isAvailable() ? "Available" : "Occupied"
                ));

        mainTable.getItems().setAll(db.getRooms());
    }
    @FXML
    private void handleCheckOut() {
        Reservation selected = (Reservation) mainTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Please select a reservation first!");
            return;
        }

        if (selected.getStatus() == ReservationStatus.CONFIRMED) {
            selected.setStatus(ReservationStatus.COMPLETED);
            mainTable.refresh();
            showAlert("Guest checked out successfully!");
        } else {
            showAlert("Cannot check-out this reservation!");
        }
    }

        @FXML
        private void handleLogout() {
            try {
                Stage stage = (Stage) welcomeLabel.getScene().getWindow();
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
    @FXML
    private void handleViewReservations() {
        mainTable.getItems().clear();

        col1.setText("ID");
        col2.setText("Guest");
        col3.setText("Room");
        col4.setText("Status");

        col1.setCellValueFactory(data ->
                new SimpleStringProperty(
                        String.valueOf(((Reservation)data.getValue()).getReservationId())
                ));

        col2.setCellValueFactory(data ->
                new SimpleStringProperty(
                        ((Reservation)data.getValue()).getGuest().getName()
                ));

        col3.setCellValueFactory(data ->
                new SimpleStringProperty(
                        ((Reservation)data.getValue()).getRoom().getRoomNumber()
                ));

        col4.setCellValueFactory(data ->
                new SimpleStringProperty(
                        ((Reservation)data.getValue()).getStatus().toString()
                ));

        mainTable.getItems().setAll(db.getReservations());
    }

    public void setStaff(Staff staff) {
        this.currentStaff = staff;
        welcomeLabel.setText("Welcome " + staff.getName());
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    }

