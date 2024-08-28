package Application;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.util.Map;

import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;

import javafx.scene.image.ImageView;

public class ShopController {
    private Stage primaryStage;
    private GameController gameController;
    private ImageView itemImageView; // ImageView 추가
    private static final Map<String, Integer> ITEM_ID_MAP = ItemData.ITEM_ID_MAP;

    public ShopController(Stage primaryStage, GameController gameController, ImageView itemImageView) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
        this.itemImageView = itemImageView;
    }

    public void showShopScreen() {
        Label shopLabel = new Label("상점");
        shopLabel.setFont(new Font(24));

        // 현재 자금을 표시하는 Label 추가
        Label fundsLabel = new Label("현재 자금: " + gameController.getPlayerFunds() + "원");
        fundsLabel.setFont(new Font(18));

        VBox vbox = new VBox(20);  // 버튼 간격을 더 크게 설정
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(20));
        vbox.getChildren().addAll(shopLabel, fundsLabel);

        // 아이템 버튼 생성
        Button basicPickaxeButton = createPurchaseButton("[단축키:1 ] 기본 곡괭이 [15만원]", 150000, "8-오팔.jpg", fundsLabel);
        Button goodPickaxeButton = createPurchaseButton("[단축키:2 ] 좋은 곡괭이 [100만원]", 1000000, "11-루비.jpg", fundsLabel);
        Button greatPickaxeButton = createPurchaseButton("[단축키:3 ] 대단한 곡괭이 [2천만원]", 20000000, "13-다이아.jpg", fundsLabel);
        Button fossilPickaxeButton = createPurchaseButton("[단축키:4 ] 화석 곡괭이 [500만원]", 5000000, "23-화석.jpg", fundsLabel);

        vbox.getChildren().addAll(basicPickaxeButton, goodPickaxeButton, greatPickaxeButton, fossilPickaxeButton);

        Button backButton = new Button("뒤로 가기");
        backButton.setFont(new Font(20)); // 폰트 크기를 키움
        backButton.setMinWidth(400);  // 최소 너비 설정
        backButton.setMinHeight(100); // 최소 높이 설정
        backButton.setOnAction(e -> gameController.showGameScreen());
        vbox.getChildren().add(backButton);

        Scene shopScene = new Scene(vbox, 1000, 800);

        // 키보드 입력으로 아이템 구매 기능 추가
        shopScene.setOnKeyPressed(event -> {
            System.out.println("Key pressed: " + event.getCode()); // 키 입력 로그 추가
            if (event.getCode() == KeyCode.ESCAPE) {
                gameController.showGameScreen();
            } else if (event.getCode() == KeyCode.DIGIT1) {
                System.out.println("Digit 1 pressed"); // 로그 추가
                basicPickaxeButton.fire();
            } else if (event.getCode() == KeyCode.DIGIT2) {
                System.out.println("Digit 2 pressed"); // 로그 추가
                goodPickaxeButton.fire();
            } else if (event.getCode() == KeyCode.DIGIT3) {
                System.out.println("Digit 3 pressed"); // 로그 추가
                greatPickaxeButton.fire();
            } else if (event.getCode() == KeyCode.DIGIT4) {
                System.out.println("Digit 4 pressed"); // 로그 추가
                fossilPickaxeButton.fire();
            }
        });

        primaryStage.setScene(shopScene);
        primaryStage.show();

        primaryStage.setScene(shopScene);
        backButton.requestFocus();
        primaryStage.show();
    }

    private Button createPurchaseButton(String itemType, int price, String imagePath, Label fundsLabel) {
        Button button = new Button(itemType + " 구매 (" + price + "원)");
        button.setFont(new Font(20)); // 폰트 크기를 키움
        button.setMinWidth(400);  // 최소 너비 설정
        button.setMinHeight(100); // 최소 높이 설정

        button.setOnAction(e -> {
            if (gameController.getPlayerFunds() >= price) {
                gameController.deductFunds(price);
                System.out.println("Funds after deduction: " + gameController.getPlayerFunds());

                // 아이템 이름을 실제 이름으로 변경
                String actualItemName = getItemNameFromImagePath(imagePath);
                System.out.println("Button Clicked. Actual Item Name: " + actualItemName);

                // 현재 아이템을 업데이트
                gameController.setCurrentItem(actualItemName); // currentItem을 업데이트

                // 이미지 업데이트
                gameController.updateImage(actualItemName);

                // 상태 및 확률 업데이트 (강화 관련)
                gameController.updateStatusAndProbability(actualItemName);

                // 자금 레이블 업데이트
                fundsLabel.setText("현재 자금: " + gameController.getPlayerFunds() + "원");

                // 성공 알림
                System.out.println("구매 성공");

                // 현재 아이템 확인
                System.out.println("Current Item after Purchase: " + gameController.getCurrentItem());
            } else {
                // 자금 부족 알림
                System.out.println("구매 실패");
            }
        });

        return button;
    }

    private String getItemNameFromImagePath(String imagePath) {
        for (Map.Entry<String, Integer> entry : ItemData.ITEM_ID_MAP.entrySet()) {
            if (imagePath.contains(entry.getValue() + "-" + entry.getKey() + ".jpg")) {
                System.out.println("Matched Item: " + entry.getKey());
                return entry.getKey();
            }
        }
        System.out.println("No match found for image path: " + imagePath);
        return "돌";
    }
}
