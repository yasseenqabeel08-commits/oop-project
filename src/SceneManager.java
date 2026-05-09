
    import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
    public class SceneManager {

        public static void switchScene(ActionEvent event, String fxmlFile) {
            try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlFile));
                Scene scene = new Scene(loader.load());

                Stage stage = (Stage) ((javafx.scene.Node) event.getSource())
                        .getScene().getWindow();
                scene.getStylesheets().add(SceneManager.class.getResource("/style.css").toExternalForm());
                stage.setScene(scene);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

