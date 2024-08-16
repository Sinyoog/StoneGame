package application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        
        Button enhanceButton = new Button("강화하기");
        enhanceButton.setOnAction(e -> enhanceStone());

        Button viewCollectionButton = new Button("도감 보기");
        viewCollectionButton.setOnAction(e -> viewCollection());

        Button shopButton = new Button("상점");
        shopButton.setOnAction(e -> openShop());

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(welcomeLabel, enhanceButton, viewCollectionButton, shopButton);

        Scene gameScene = new Scene(vbox, 400, 300);
        primaryStage.setScene(gameScene);
    }

    private void enhanceStone() {
        // 돌 강화 로직
    }

    private void viewCollection() {
        // 보석 도감 보기 로직
    }

    private void openShop() {
        // 상점 열기 로직
    }
}
