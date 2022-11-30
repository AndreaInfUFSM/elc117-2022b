package hellofx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML TextField nameText;
    @FXML Label greeting;
    
    @FXML
    void doGreeting(ActionEvent event) {
        String name = nameText.getText();
        String message = "Hello, "+name+". Welcome to JavaFX!";
        greeting.setText(message);
    }
}