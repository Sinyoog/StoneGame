package Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.Map;
import java.util.HashMap;

public class GameController {
    private Stage primaryStage;
    private String playerId;
    private int playerFunds;
    private String[] inventory;
    private ImageView imageView;
    private SellController sellController;
    private String currentItem; // 현재 표시된 아이템 이름을 저장하는 필드

    // 아이템 ID 정의
    private static final Map<String, Integer> ITEM_ID_MAP = new HashMap<>() {{
        put("돌", 1);
        put("석영", 2);
        put("자수정", 3);
        put("시트린", 4);
        put("페리도트", 5);
        put("토파즈", 6);
        put("탄자나이트", 7);
        put("오팔", 8);
        put("사파이어", 9);
        put("에메랄드", 10);
        put("루비", 11);
        put("알렉산드라이트", 12);
        put("다이아몬드", 13);
        put("세렌디바이트", 14);
        put("타파이트", 15);
        put("우라늄", 16);
        put("완벽한돌", 17);
    }};

    // 강화 확률 정의
    private static final Map<String, Double> ENHANCE_PROBABILITY_MAP = new HashMap<>() {{
        put("돌", 0.0);
        put("석영", 80.0);
        put("자수정", 95.0);
        put("시트린", 90.0);
        put("페리도트", 85.0);
        put("토파즈", 80.0);
        put("탄자나이트", 75.0);
        put("오팔", 70.0);
        put("사파이어", 60.0);
        put("에메랄드", 50.0);
        put("루비", 40.0);
        put("알렉산드라이트", 30.0);
        put("다이아몬드", 20.0);
        put("세렌디바이트", 10.0);
        put("타파이트", 5.0);
        put("우라늄", 1.0);
        put("완벽한돌", 0.05);
    }};

    public GameController(Stage primaryStage, String playerId, int initialFunds, String[] initialInventory) {
        this.primaryStage = primaryStage;
        this.playerId = playerId;
        this.playerFunds = initialFunds;
        this.inventory = initialInventory != null ? initialInventory : new String[0];
        this.imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        this.sellController = new SellController();
        this.currentItem = "돌";
    }

    public Integer getItemId(String itemName) {
        return ITEM_ID_MAP.get(itemName); // ITEM_ID_MAP1 대신 ITEM_ID_MAP 사용
    }

    public String getItemNameById(int id) {
        for (Map.Entry<String, Integer> entry : ITEM_ID_MAP.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Double getEnhanceProbability(String itemName) {
        Double probability = ENHANCE_PROBABILITY_MAP.get(itemName);
        if (probability == null) {
            System.out.println("No enhance probability found for: " + itemName);
            return 0.0;
        }
        System.out.println("Retrieved enhance probability for " + itemName + ": " + probability);
        return probability;
    }

    public Integer getItemPrice(String itemName) {
        return sellController.calculateSellingPrice(itemName); // SellController에서 가격 계산
    }

    private void setupEnhanceButton(Button enhanceButton) {
        enhanceButton.setOnAction(e -> enhanceItem(currentItem)); // 현재 아이템으로 강화하기
    }

    public void showGameScreen() {
        Label welcomeLabel = new Label("사용자: " + playerId);
        welcomeLabel.setFont(new Font(24));
        Label fundsLabel = new Label("현재 자금: " + playerFunds);
        fundsLabel.setFont(new Font(24));

        VBox topLeftBox = new VBox(10);
        topLeftBox.getChildren().addAll(welcomeLabel, fundsLabel);
        topLeftBox.setAlignment(Pos.TOP_LEFT);
        topLeftBox.setPadding(new Insets(10));

        Button enhanceButton = new Button("강화하기");
        setupEnhanceButton(enhanceButton);

        Button viewCollectionButton = new Button("도감 보기");
        viewCollectionButton.setOnAction(e -> openCollectionScreen());

        Button inventoryButton = new Button("인벤토리");
        inventoryButton.setOnAction(e -> openInventoryScreen());

        Button shopButton = new Button("상점");
        shopButton.setOnAction(e -> openShopScreen());

        Button sellButton = new Button("판매하기");
        sellButton.setOnAction(e -> {
            sellItem(); // 판매하기 기능 호출
            updateImage("돌"); // 판매 후 기본 이미지를 표시
        });

        VBox centerVBox = new VBox(10);
        centerVBox.getChildren().addAll(imageView, enhanceButton);
        centerVBox.setAlignment(Pos.CENTER);

        VBox rightTopVBox = new VBox(10);
        rightTopVBox.getChildren().add(viewCollectionButton);
        rightTopVBox.setAlignment(Pos.TOP_RIGHT);
        rightTopVBox.setPadding(new Insets(10));

        VBox rightBottomVBox = new VBox(10);
        rightBottomVBox.getChildren().add(sellButton);
        rightBottomVBox.setAlignment(Pos.BOTTOM_RIGHT);
        rightBottomVBox.setPadding(new Insets(10));

        VBox leftBottomVBox = new VBox(10);
        leftBottomVBox.getChildren().add(shopButton);
        leftBottomVBox.setAlignment(Pos.BOTTOM_LEFT);
        leftBottomVBox.setPadding(new Insets(10));

        VBox rightMiddleVBox = new VBox(10);
        rightMiddleVBox.getChildren().addAll(viewCollectionButton, inventoryButton);
        rightMiddleVBox.setAlignment(Pos.CENTER_RIGHT);
        rightMiddleVBox.setPadding(new Insets(10));

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topLeftBox);
        borderPane.setCenter(centerVBox);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setLeft(leftBottomVBox);
        bottomPane.setRight(rightBottomVBox);

        BorderPane rightPane = new BorderPane();
        rightPane.setTop(rightTopVBox);
        rightPane.setCenter(rightMiddleVBox);

        borderPane.setRight(rightPane);
        borderPane.setBottom(bottomPane);

        // 초기 이미지를 표시
        updateImage(currentItem);

        Scene gameScene = new Scene(borderPane, 1000, 800);
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // E 키를 눌렀을 때 강화하기 버튼 기능 수행
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                enhanceItem(currentItem); // 현재 아이템으로 강화하기
            }
        });
    }

    private void openInventoryScreen() {
        InventoryController inventoryController = new InventoryController(primaryStage, inventory, this);
        inventoryController.showInventoryScreen();
    }

    private void openCollectionScreen() {
        ViewCollectionController viewCollectionController = new ViewCollectionController(primaryStage, this);
        viewCollectionController.showCollectionScreen();
    }

    private void openShopScreen() {
        ShopController shopController = new ShopController(primaryStage, this);
        shopController.showShopScreen();
    }

    public void updateImage(String itemName) {
        currentItem = itemName; // 현재 표시된 아이템 이름을 업데이트
        Integer itemId = ITEM_ID_MAP.get(itemName); // ITEM_ID_MAP 사용
        if (itemId == null) {
            System.out.println("Item ID not found for: " + itemName);
            return;
        }
        String imagePath = "/image/" + itemId + "-" + itemName + ".jpg";
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.out.println("Image not found: " + imagePath);
            return;
        }
        Image image = new Image(imageStream);
        imageView.setImage(image);
    }

    public void enhanceItem(String currentItem) {
        Integer currentItemId = getItemId(currentItem);
        if (currentItemId == null) {
            System.out.println("Invalid item: " + currentItem);
            return;
        }

        Integer nextItemId = currentItemId + 1;
        String nextItem = getItemNameById(nextItemId);
        if (nextItem == null) {
            System.out.println("No further enhancement possible.");
            updateImage(currentItem);
            return;
        }

        Double probability = getEnhanceProbability(nextItem);
        if (probability == null) {
            probability = 0.0;
        }

        boolean isSuccess = determineEnhanceSuccess(probability);
        System.out.println("Enhance success: " + isSuccess + " for item: " + currentItem);

        if (isSuccess) {
            updateImage(nextItem);
        } else {
            updateImage("돌");
        }
    }

    private boolean determineEnhanceSuccess(double probability) {
        double randomValue = Math.random() * 100;
        return randomValue <= probability;
    }

    private void updateFundsLabel() {
        Label fundsLabel = (Label) ((VBox) ((BorderPane) primaryStage.getScene().getRoot()).getTop()).getChildren().get(1);
        fundsLabel.setText("현재 자금: " + playerFunds);
    }

    public void sellItem() {
        if (currentItem != null) {
            int sellingPrice = sellController.calculateSellingPrice(currentItem);
            playerFunds += sellingPrice;
            System.out.println(currentItem + "를 판매했습니다. 판매 금액: " + sellingPrice + " 현재 자금: " + playerFunds);
            updateImage("돌"); // 판매 후 기본 이미지를 표시
            currentItem = "돌"; // 현재 아이템을 기본 아이템으로 초기화
            updateFundsLabel(); // 자금 레이블 업데이트
        } else {
            System.out.println("판매할 아이템이 없습니다.");
        }
    }


    private String getCurrentItem() {
        // 현재 이미지의 이름을 얻는 메소드 구현 필요
        // 예를 들어, 현재 이미지 파일의 이름에서 아이템 이름을 추출하는 방식
        return currentItem; // 현재 아이템을 반환
    }
}
