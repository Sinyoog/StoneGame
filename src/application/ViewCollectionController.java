package application;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

public class ViewCollectionController {
    private Stage primaryStage;
    private GameController gameController; // GameController 인스턴스 추가

    public ViewCollectionController(Stage primaryStage, GameController gameController) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
    }

    public void showCollectionScreen() {
        GridPane collectionGrid = new GridPane();
        collectionGrid.setHgap(10);
        collectionGrid.setVgap(10);

        for (int i = 0; i < 35; i++) {
            Label emptySlot = new Label("Item " + (i + 1));
            emptySlot.setPrefSize(100, 100);
            emptySlot.setStyle("-fx-border-color: black; -fx-background-color: lightgray;");
            int col = i % 7;
            int row = i / 7;
            collectionGrid.add(emptySlot, col, row);
        }

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(new Label("보석 도감"), collectionGrid);
        vbox.setAlignment(Pos.CENTER);

        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> gameController.showGameScreen()); // GameController의 showGameScreen 호출

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(vbox, backButton);

        Scene collectionScene = new Scene(mainLayout, 1000, 800);

        // ESC 키를 눌렀을 때 뒤로 가기 버튼의 기능을 수행하도록 설정
        collectionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                gameController.showGameScreen(); // GameController의 showGameScreen 호출
            }
        });

        primaryStage.setScene(collectionScene);

        // 포커스 요청을 올바른 노드에서 수행
        mainLayout.requestFocus(); // VBox 노드에 포커스 요청
    }
}
