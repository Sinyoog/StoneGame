package Application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.io.InputStream;

public class EnhanceStoneController {
    private Stage primaryStage;
    private GameController gameController;
    private ImageView resultImageView;

    public EnhanceStoneController(Stage primaryStage, GameController gameController) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
        this.resultImageView = new ImageView();
        resultImageView.setFitWidth(400);
        resultImageView.setFitHeight(400);
        resultImageView.setPreserveRatio(true);
    }

    public void showEnhanceScreen() {
        BorderPane borderPane = new BorderPane();
        VBox vbox = new VBox();

        // 강화할 아이템 이름을 입력받기 위한 UI 구성
        Label label = new Label("아이템 이름 입력:");
        Button enhanceButton = new Button("강화하기");

        enhanceButton.setOnAction(e -> {
            String itemName = "돌"; // 예제에서는 고정된 아이템 이름을 사용
            enhanceItem(itemName);
        });

        vbox.getChildren().addAll(label, enhanceButton, resultImageView);
        borderPane.setCenter(vbox);

        Scene scene = new Scene(borderPane, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void enhanceItem(String itemName) {
        if (itemName == null || itemName.isEmpty()) {
            System.out.println("Item name is required for enhancement.");
            return;
        }

        // 강화 확률을 가져옵니다.
        Double probability = gameController.getEnhanceProbability(itemName);

        if (probability != null) {
            boolean isSuccess = Math.random() * 100 <= probability;
            if (isSuccess) {
                System.out.println(itemName + " 강화 성공!");
                // 강화 성공 시 이미지 업데이트
                String nextItem = getNextItem(itemName);
                if (nextItem != null) {
                    gameController.updateImage(nextItem); // 강화된 아이템 이미지로 업데이트
                    updateResultImage(true); // 성공 이미지로 업데이트
                } else {
                    System.out.println("No next item found for: " + itemName);
                    updateResultImage(false); // 실패 이미지로 업데이트
                }
            } else {
                System.out.println(itemName + " 강화 실패!");
                // 강화 실패 시 초기 단계로 돌아가기
                gameController.updateImage("돌");
                updateResultImage(false); // 실패 이미지로 업데이트
            }
        } else {
            System.out.println("No enhance probability found for " + itemName);
        }
    }

    private String getNextItem(String currentItem) {
        // 현재 아이템의 ID를 가져와서 다음 아이템을 반환합니다.
        int currentItemId = gameController.getItemId(currentItem);
        int nextItemId = currentItemId + 1; // 다음 단계 아이템 ID
        return gameController.getItemNameById(nextItemId);
    }

    private void updateResultImage(boolean success) {
        String imagePath = success ? "/images/enhanced.jpg" : "/images/original.jpg";
        try (InputStream imageStream = getClass().getResourceAsStream(imagePath)) {
            if (imageStream == null) {
                throw new IllegalArgumentException("Image file not found: " + imagePath);
            }
            Image image = new Image(imageStream);
            resultImageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
