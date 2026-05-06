import javafx.event.ActionEvent;

public class WelcomeController {

    public void handleLogin(ActionEvent event) {
        SceneManager.switchScene(event, "login.fxml");
    }

    public void handleRegister(ActionEvent event) {
        SceneManager.switchScene(event, "register.fxml");
    }
}