package hellofx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class App extends Application {

  @Override
  public void start(Stage stage) {

    Label label = new Label("Enter your name:");
    TextField name = new TextField();
    Button button = new Button("Greet Me!");
    Label greeting = new Label("");

    // JavaFX generates an event (ActionEvent) when the user clicks the button
    // We handle the event by updating the greeting label
    // This code uses an anonymous class: https://www.baeldung.com/java-anonymous-classes
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        greeting.setText("Hello, " + name.getText() + ". Welcome to JavaFX!");
      }
    });

    // Since Java 8, we can shorten the code of the event handler
    // See: https://www.baeldung.com/javafx-button-eventhandler
    // button.setOnAction(e -> greeting.setText("Hello, " + name.getText() + ". Welcome to JavaFX!"));

    VBox vbox = new VBox(label, name, button, greeting);
    vbox.setSpacing(20);
    vbox.setAlignment(Pos.BASELINE_CENTER);
    Scene scene = new Scene(vbox, 320, 240);

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }

}
