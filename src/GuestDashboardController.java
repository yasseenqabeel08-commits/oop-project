import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.concurrent.Task;
import model.*;
import HotelRooms.*;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.property.SimpleStringProperty;
public class GuestDashboardController {

        @FXML
        private Label welcomeLabel;

        @FXML
        private TextArea displayArea;
    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TableColumn<Room, String> colRoomNumber;

    @FXML
    private TableColumn<Room, String> colPrice;

        private Guest currentGuest;
        private HotelDataBase db = HotelDataBase.getInstance();

    @FXML
    public void initialize() {
        if (currentGuest != null) {
            welcomeLabel.setText("Welcome " + currentGuest.getName());
        }

        colRoomNumber.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getRoomNumber())
        );

        colPrice.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(
                        String.valueOf(data.getValue().getPricePerNight())
                )
        );
    }

    public void setGuest(Guest guest) {
        this.currentGuest = guest;

        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome " + guest.getName());
        }
    }

        @FXML
        private void handleProfile() {
            displayArea.setText(
                    "Name: " + currentGuest.getName() +
                            "\nBalance: " + currentGuest.getBalance()
            );
        }

        @FXML
        private void handleBrowseRooms() {

                Task<ObservableList<Room>> task = new Task<>() {
                    @Override
                    protected ObservableList<Room> call() throws Exception {
                        Thread.sleep(1000);
                        return FXCollections.observableArrayList(db.getRooms());
                    }
                };

                task.setOnSucceeded(e -> {
                    roomTable.setItems(task.getValue());
                });

                Thread thread = new Thread(task);
                thread.setDaemon(true);
                thread.start();
            }


        @FXML
        private void handleMyReservations() {
            StringBuilder sb = new StringBuilder("My Reservations:\n");

            for (Reservation r : db.getReservations()) {
                if (r.getGuest().equals(currentGuest)) {
                    sb.append("Reservation ID: ").append(r.getReservationId())
                            .append(" | Room: ").append(r.getRoom().getRoomNumber())
                            .append("\n");
                }
            }

            displayArea.setText(sb.toString());
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
              //  stage.setScene(new Scene(root));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

