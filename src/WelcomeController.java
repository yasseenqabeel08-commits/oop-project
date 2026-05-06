
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class WelcomeController {

        private void switchScene(ActionEvent event, String fxmlFile) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/" + fxmlFile));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                        .getScene().getWindow();

                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void handleLogin(ActionEvent event) {
            switchScene(event, "login.fxml");
        }

        public void handleRegister(ActionEvent event) {
            switchScene(event, "register.fxml");
        }
    }
