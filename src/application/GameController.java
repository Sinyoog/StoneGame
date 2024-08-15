package application;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameController {
    private Stage primaryStage;
    private String playerId;

    public GameController(Stage primaryStage, String playerId) {
        this.primaryStage = primaryStage;
        this.playerId = playerId;
    }

    public void showGameScreen() {
        Label welcomeLabel = new Label("Welcome, " + playerId + "!");
        VBox vbox = new VBox(10);
        vbox.getChildren().add(welcomeLabel);

        Scene gameScene = new Scene(vbox, 400, 300);
        primaryStage.setScene(gameScene);
    }
}
