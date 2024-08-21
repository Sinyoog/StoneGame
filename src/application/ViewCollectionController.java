package Application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

public class ViewCollectionController {
    private Stage primaryStage;
    private GameController gameController;

    public ViewCollectionController(Stage primaryStage, GameController gameController) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
    }

    public void showCollectionScreen() {
        GridPane collectionGrid = new GridPane();
        collectionGrid.setHgap(10);
        collectionGrid.setVgap(10);
        collectionGrid.setPadding(new Insets(10));

        // 7열 x 5행의 총 35개의 칸을 생성
        for (int i = 0; i < 35; i++) {
            String imagePath = "file:/C:/Users/MY/OneDrive/바탕 화면/StoneGame/Project/image/" + (i + 1) + ".jpg";
            Image image = null;
            
            // 이미지 로드 시도
            try {
                image = new Image(imagePath);
            } catch (Exception e) {
                // 이미지 로드 실패 시
            }

            ImageView imageView = new ImageView();
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);
            imageView.setPreserveRatio(true);

            if (image != null && !image.isError()) {
                imageView.setImage(image);
            } else {
                // 이미지 로드 실패 시, 회색 네모칸을 추가
                Rectangle greyRectangle = new Rectangle(100, 100, Color.GREY);
                collectionGrid.add(greyRectangle, i % 7, i / 7);
            }
            
            // 이미지가 존재하면 ImageView를 추가
            collectionGrid.add(imageView, i % 7, i / 7);
        }

        Label titleLabel = new Label("보석 도감");
        titleLabel.setFont(new javafx.scene.text.Font(24));  // 제목 폰트 크기 설정

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, collectionGrid);
        vbox.setAlignment(Pos.CENTER);

        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> gameController.showGameScreen());

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(vbox, backButton);

        Scene collectionScene = new Scene(mainLayout, 1000, 800);
        collectionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                gameController.showGameScreen();
            }
        });

        primaryStage.setScene(collectionScene);
        mainLayout.requestFocus();
    }
}
