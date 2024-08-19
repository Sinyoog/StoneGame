package application;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

public class InventoryController {
    private Stage primaryStage;
    private String[] inventory;
    private GameController gameController; // GameController 인스턴스 추가

    public InventoryController(Stage primaryStage, String[] inventory, GameController gameController) {
        this.primaryStage = primaryStage;
        this.inventory = inventory;
        this.gameController = gameController;
    }

    public void showInventoryScreen() {
        VBox inventoryVBox = new VBox(10);
        inventoryVBox.setAlignment(Pos.CENTER);
        inventoryVBox.setPadding(new Insets(20));

        // 인벤토리 아이템 목록
        Label inventoryLabel = new Label("인벤토리");
        inventoryLabel.setFont(new Font(24)); // 글자 크기 조정
        inventoryVBox.getChildren().add(inventoryLabel);

        // 인벤토리 아이템 표시
        if (inventory.length > 0) {
            VBox itemsVBox = new VBox(5);
            itemsVBox.setAlignment(Pos.CENTER);
            for (String item : inventory) {
                itemsVBox.getChildren().add(new Label(item));
            }
            inventoryVBox.getChildren().add(itemsVBox);
        } else {
            inventoryVBox.getChildren().add(new Label("인벤토리에 아이템이 없습니다."));
        }

        // 뒤로 가기 버튼
        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> gameController.showGameScreen()); // GameController의 showGameScreen 호출
        inventoryVBox.getChildren().add(backButton);

        Scene inventoryScene = new Scene(inventoryVBox, 1000, 800);
        primaryStage.setScene(inventoryScene);
        
     // ESC 키를 눌렀을 때 뒤로 가기 버튼의 기능을 수행하도록 설정
        inventoryScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                gameController.showGameScreen();
            }
        });
    }
}
