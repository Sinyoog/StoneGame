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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;

import java.io.InputStream;
import java.util.Map;

public class GameController {
    private Stage primaryStage;
    private String playerId;
    private int playerFunds;
    private String[] inventory;
    private ImageView imageView;

    private SellController sellController;
    private EnhanceStoneController enhanceStoneController;
    private ItemValidator itemValidator; 
    private String currentItem;

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
        this.enhanceStoneController = new EnhanceStoneController(imageView, ItemData.ITEM_ID_MAP);
        this.itemValidator = new ItemValidator(); 
        this.currentItem = "돌";
    }

    public Integer getItemId(String itemName) {
        return ItemData.ITEM_ID_MAP.get(itemName);
    }

    public String getItemNameById(int id) {
        for (Map.Entry<String, Integer> entry : ItemData.ITEM_ID_MAP.entrySet()) {
            if (entry.getValue() == id) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Double getEnhanceProbability(String itemName) {
        return enhanceStoneController.getEnhanceProbability(itemName);
    }

    public Integer getItemPrice(String itemName) {
        return sellController.calculateSellingPrice(itemName);
    }

    private void setupEnhanceButton(Button enhanceButton) {
        enhanceButton.setOnAction(e -> {
            if (enhanceStoneController.enhanceItem(currentItem)) {
                updateImage(currentItem); 
            } else {
                showAlert("강화 실패", "더 이상 진화 단계가 없습니다. 다른 루트를 찾으세요.");
                updateImage("돌"); 
                currentItem = "돌";
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showGameScreen() {
        Label welcomeLabel = new Label("사용자: " + playerId);
        welcomeLabel.setFont(new Font(24));
        Label fundsLabel = new Label("현재 자금: " + playerFunds + "원");
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
            sellItem(); 
            updateImage("돌"); 
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

        updateImage(currentItem);

        Scene gameScene = new Scene(borderPane, 1000, 800);
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                enhanceItem(currentItem); 
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

    public boolean enhanceItem(String currentItem) {
        itemValidator.validateItem(currentItem);

        EnhanceStage stage = enhanceStoneController.getEnhanceStage(currentItem);
        if (stage == null) {
            System.out.println("Invalid item: " + currentItem);
            return false;
        }

        if (stage.getNextStages().isEmpty()) {
            System.out.println("이 진화 루트에서 마지막 단계입니다.");
            updateImage(currentItem);
            return false;
        }

        double randomValue = Math.random() * 100;
        double cumulativeProbability = 0.0;

        for (Map.Entry<String, Double> entry : stage.getNextStages().entrySet()) {
            cumulativeProbability += entry.getValue();
            if (randomValue <= cumulativeProbability) {
                this.currentItem = entry.getKey();
                updateImage(this.currentItem);
                return true;
            }
        }

        // 실패 시 돌로 초기화
        System.out.println("강화 실패: " + currentItem + ". 돌로 초기화합니다.");
        this.currentItem = "돌";
        updateImage(this.currentItem);
        return false;
    }

    public void updateImage(String itemName) {
        Integer itemId = ItemData.ITEM_ID_MAP.get(itemName);
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

    private void updateFundsLabel() {
        Label fundsLabel = (Label) ((VBox) ((BorderPane) primaryStage.getScene().getRoot()).getTop()).getChildren().get(1);
        fundsLabel.setText("현재 자금: " + playerFunds);
    }

    public void sellItem() {
        if (currentItem != null) {
            int sellingPrice = sellController.calculateSellingPrice(currentItem);
            playerFunds += sellingPrice;
            System.out.println(currentItem + "를 판매했습니다. 판매 금액: " + sellingPrice + " 현재 자금: " + playerFunds);
            
            // 돌로 초기화
            currentItem = "돌";
            updateImage(currentItem);
            updateFundsLabel(); // 자금 레이블 업데이트
        } else {
            System.out.println("판매할 아이템이 없습니다.");
        }
    }
}
