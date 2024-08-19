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
import javafx.application.Platform;


public class ShopController {
    private Stage primaryStage;
    private GameController gameController; // GameController 인스턴스 추가

    public ShopController(Stage primaryStage, GameController gameController) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
    }

    public void showShopScreen() {
        // 상점 제목 라벨 설정
        Label shopLabel = new Label("상점");
        shopLabel.setFont(new Font(24)); // 글자 크기 조정

        // VBox 생성 및 설정
        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20)); // 패딩 설정
        vbox.getChildren().add(shopLabel);

        // 뒤로 가기 버튼 생성 및 설정
        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> gameController.showGameScreen()); // 뒤로 가기 버튼 클릭 시 게임 화면으로 이동

        // VBox에 뒤로 가기 버튼 추가
        vbox.getChildren().add(backButton);

        // 새로운 장면(Scene) 생성
        Scene shopScene = new Scene(vbox, 1000, 800);

        // ESC 키 눌렀을 때 뒤로 가기 버튼과 동일한 동작을 수행
        shopScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                gameController.showGameScreen();
            }
        });

        primaryStage.setScene(shopScene);
        primaryStage.show();

        // 뒤로 가기 버튼에 포커스 요청
        backButton.requestFocus(); // 버튼에 포커스 요청
    }

}