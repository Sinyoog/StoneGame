package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import java.util.Arrays;

public class GameController {
    private Stage primaryStage;
    private String playerId;
    private int playerFunds; // 현재 자금
    private String[] inventory; // 소지 아이템

    // 생성자 수정: Stage, String, int, String[]를 매개변수로 받도록 수정
    public GameController(Stage primaryStage, String playerId, int initialFunds, String[] initialInventory) {
        this.primaryStage = primaryStage;
        this.playerId = playerId;
        this.playerFunds = initialFunds;
        this.inventory = initialInventory != null ? initialInventory : new String[0]; // 초기화 방지
    }

    public void showGameScreen() {
        // 사용자와 자금 라벨
        Label welcomeLabel = new Label("사용자: " + playerId);
        welcomeLabel.setFont(new Font(24)); // 글자 크기 조정
        Label fundsLabel = new Label("현재 자금: " + playerFunds); // 현재 자금 표시
        fundsLabel.setFont(new Font(24)); // 글자 크기 조정

        // VBox에 라벨들을 추가하고, 이를 BorderPane의 상단에 배치
        VBox topLeftBox = new VBox(10); // 라벨 간의 간격 10px
        topLeftBox.getChildren().addAll(welcomeLabel, fundsLabel);
        topLeftBox.setAlignment(Pos.TOP_LEFT); // 좌측 상단 정렬
        topLeftBox.setPadding(new Insets(10)); // 패딩 추가

        // 버튼들
        Button enhanceButton = new Button("강화하기");
        enhanceButton.setOnAction(e -> openEnhanceScreen());

        Button viewCollectionButton = new Button("도감 보기");
        viewCollectionButton.setOnAction(e -> openCollectionScreen());

        Button inventoryButton = new Button("인벤토리");
        inventoryButton.setOnAction(e -> openInventoryScreen());

        Button shopButton = new Button("상점");
        shopButton.setOnAction(e -> openShopScreen());

        Button sellButton = new Button("판매하기");
        sellButton.setOnAction(e -> sellItem()); // 판매 버튼 클릭 시 호출

        // 중앙 버튼을 포함할 VBox
        VBox centerVBox = new VBox(10);
        centerVBox.getChildren().add(enhanceButton);
        centerVBox.setAlignment(Pos.CENTER);

        // 우측 상단 버튼을 포함할 VBox
        VBox rightTopVBox = new VBox(10);
        rightTopVBox.getChildren().add(viewCollectionButton);
        rightTopVBox.setAlignment(Pos.TOP_RIGHT);
        rightTopVBox.setPadding(new Insets(10)); // 패딩 추가

        // 우측 하단 버튼을 포함할 VBox
        VBox rightBottomVBox = new VBox(10);
        rightBottomVBox.getChildren().add(sellButton);
        rightBottomVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightBottomVBox.setPadding(new Insets(10)); // 패딩 추가

        // 좌측 하단 버튼을 포함할 VBox
        VBox leftBottomVBox = new VBox(10);
        leftBottomVBox.getChildren().add(shopButton);
        leftBottomVBox.setAlignment(Pos.BOTTOM_LEFT);
        leftBottomVBox.setPadding(new Insets(10)); // 패딩 추가

        // 도감 보기 버튼 아래에 인벤토리 버튼을 포함할 VBox
        VBox rightMiddleVBox = new VBox(10);
        rightMiddleVBox.getChildren().addAll(viewCollectionButton, inventoryButton);
        rightMiddleVBox.setAlignment(Pos.CENTER_RIGHT);
        rightMiddleVBox.setPadding(new Insets(10)); // 패딩 추가

        // BorderPane을 사용하여 레이아웃 설정
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topLeftBox); // 상단에 사용자와 자금 라벨 배치
        borderPane.setCenter(centerVBox); // 중앙에 강화하기 버튼 배치

        // 하단에 좌측과 우측 버튼 패널 배치
        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(leftBottomVBox); // 좌측 하단 버튼
        bottomPane.setRight(rightBottomVBox); // 우측 하단 버튼

        // 우측 상단과 중앙 버튼 패널 배치
        BorderPane rightPane = new BorderPane();
        rightPane.setTop(rightTopVBox); // 우측 상단 버튼
        rightPane.setCenter(rightMiddleVBox); // 도감 보기와 인벤토리 버튼

        // BorderPane에 우측 패널 배치
        borderPane.setRight(rightPane);

        // 하단 버튼 패널을 하단에 배치
        borderPane.setBottom(bottomPane);

        Scene gameScene = new Scene(borderPane, 1000, 800);
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    private void openInventoryScreen() {
        InventoryController inventoryController = new InventoryController(primaryStage, inventory, this);
        inventoryController.showInventoryScreen();
    }
    
    private void openEnhanceScreen() {
        EnhanceStoneController enhanceController = new EnhanceStoneController(primaryStage);
        enhanceController.showEnhanceScreen();
    }

    private void openCollectionScreen() {
        ViewCollectionController viewCollectionController = new ViewCollectionController(primaryStage, this);
        viewCollectionController.showCollectionScreen();
    }

    private void openShopScreen() {
        ShopController shopController = new ShopController(primaryStage, this);
        shopController.showShopScreen();
    }

    private void sellItem() {
        // 아이템 판매 로직을 여기에 추가
        if (inventory.length > 0) {
            // 첫 번째 아이템을 판매하는 예제
            String itemToSell = inventory[0];
            int sellingPrice = calculateSellingPrice(itemToSell);

            // 자금 업데이트
            playerFunds += sellingPrice;

            // 아이템 목록 업데이트
            inventory = removeItemFromInventory(inventory, itemToSell);

            // 업데이트된 자금과 아이템 목록을 화면에 반영
            showGameScreen(); // 자금과 아이템 목록을 갱신하여 다시 화면 표시
        } else {
            showError("판매할 아이템이 없습니다.");
        }
    }

    private int calculateSellingPrice(String item) {
        // 아이템에 따른 판매 가격을 계산하는 로직
        return 100; // 예제 가격
    }

    private String[] removeItemFromInventory(String[] inventory, String itemToRemove) {
        return Arrays.stream(inventory)
                     .filter(item -> !item.equals(itemToRemove))
                     .toArray(String[]::new);
    }

    private void showError(String message) {
        Stage errorStage = new Stage();
        VBox vbox = new VBox(new Label(message));
        Scene scene = new Scene(vbox, 200, 100);
        errorStage.setScene(scene);
        errorStage.show();
    }
}
