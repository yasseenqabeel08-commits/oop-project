import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.concurrent.Task;
import model.*;
import HotelRooms.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
public class GuestDashboardController {

        @FXML
        private Label welcomeLabel;

//        @FXML
//        private TextArea displayArea;
//    @FXML
//    private TableView<Room> roomTable;
//
//    @FXML
//    private TableColumn<Room, String> colRoomNumber;
//    @FXML
//    private TableColumn<Room, String> colPrice;
//
    @FXML private TableView<Reservation> bookingTable;
    @FXML private TableColumn<Reservation, String> colRoom;
    @FXML private TableColumn<Reservation, String> colCheckIn;
    @FXML private TableColumn<Reservation, String> colCheckOut;
    @FXML private VBox profilePane;
    @FXML private VBox roomsPane;
    @FXML private VBox reservationPane;
        private Guest currentGuest;
    @FXML private Label balanceLabel;
        private HotelDataBase db = HotelDataBase.getInstance();
    @FXML private Label nameLabel;
    @FXML private Label balanceInfoLabel;
    @FXML private Label addressLabel;
    @FXML private TableView<Reservation> reservationTable;
    @FXML
    public void initialize() {
        if (currentGuest != null) {
            welcomeLabel.setText("Welcome " + currentGuest.getName());
        }

//        colRoomNumber.setCellValueFactory(data ->
//                new javafx.beans.property.SimpleStringProperty(data.getValue().getRoomNumber())
//        );
//
//        colPrice.setCellValueFactory(data ->
//                new javafx.beans.property.SimpleStringProperty(
//                        String.valueOf(data.getValue().getPricePerNight())
//                )
//        );
        colRoom.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getRoom().getRoomNumber()
                )
        );

        colCheckIn.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckInDate().toString()
                )
        );

        colCheckOut.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        data.getValue().getCheckOutDate().toString()
                )
        );
    }

    public void setGuest(Guest guest) {
        this.currentGuest = guest;

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome " + guest.getName());
        }
        showProfileInfo();
        balanceInfoLabel.setText("Balance: " + guest.getBalance());
        loadBookings();
    }

    @FXML
    private void handleProfile() {

        profilePane.setVisible(true);
        roomsPane.setVisible(false);
        reservationPane.setVisible(false);
        nameLabel.setText("Name: " + currentGuest.getName());
        balanceInfoLabel.setText("Balance: " + currentGuest.getBalance());
        addressLabel.setText("Address: " + currentGuest.getAddress());
    }


    @FXML
    private void handleBrowseRooms() {
        profilePane.setVisible(false);
        roomsPane.setVisible(true);
        reservationPane.setVisible(false);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("rooms.fxml"));
            Parent root = loader.load();

            // get controller
            RoomsController controller = loader.getController();

            //  pass guest
            controller.setGuest(currentGuest);

            Stage stage = (Stage) welcomeLabel.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        @FXML
        private void handleMyReservations() {
            profilePane.setVisible(false);
            roomsPane.setVisible(false);
            reservationPane.setVisible(true);
            StringBuilder sb = new StringBuilder("My Reservations:\n");
            for (Reservation r : db.getReservations()) {
                if (r.getGuest().equals(currentGuest)) {
                    sb.append("Reservation ID: ").append(r.getReservationId())
                            .append(" | Room: ").append(r.getRoom().getRoomNumber())
                            .append("\n");
                }
            }

//            displayArea.setText(sb.toString());
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
              //  stage.setScene(new Scene(root));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    private void loadBookings() {

        ObservableList<Reservation> list = FXCollections.observableArrayList();

        for (Reservation r : db.getReservations()) {
            if (r.getGuest().equals(currentGuest)) {
                list.add(r);
            }
        }

        bookingTable.setItems(list);
    }
    private void showProfileInfo() {
        nameLabel.setText("Name: " + currentGuest.getName());
        balanceInfoLabel.setText("Balance: " + currentGuest.getBalance());
        addressLabel.setText("Address: " + currentGuest.getAddress());
    }
    @FXML
    private void handleCancelReservation() {

        Reservation selected = bookingTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a reservation first!");
            alert.showAndWait();
            return;
        }

        // confirmation dialog
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Cancel Reservation");
        confirm.setHeaderText("Are you sure?");
        confirm.setContentText("Do you want to cancel this reservation?");

        if (confirm.showAndWait().get() == ButtonType.OK) {

            db.getReservations().remove(selected);

            loadBookings();

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Success");
            success.setHeaderText(null);
            success.setContentText("Reservation cancelled successfully!");
            success.showAndWait();
        }
    }

    }

