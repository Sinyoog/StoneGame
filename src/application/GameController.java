package Application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
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
    private String itemName;
    private ImageView imageView;
    private Label statusLabel;
    private Label probabilityLabel;
    private ImageView itemImageView;
    private BorderPane borderPane;

    private SellController sellController;
    private EnhanceStoneController enhanceStoneController;
    private ItemValidator itemValidator;
    private String currentItem;
    private ViewCollectionController viewCollectionController;
    

    public GameController(Stage primaryStage, String playerId, int initialFunds, String[] initialInventory) {
        this.primaryStage = primaryStage;
        this.playerId = playerId;
        this.playerFunds = initialFunds;
        this.inventory = initialInventory != null ? initialInventory : new String[0];
        this.imageView = new ImageView();
        imageView.setFitWidth(400);
        imageView.setFitHeight(400);
        imageView.setPreserveRatio(true);
        itemImageView = new ImageView();
        
        this.statusLabel = new Label();
        statusLabel.setFont(new Font(18));
        this.probabilityLabel = new Label();
        probabilityLabel.setFont(new Font(18));
        
        this.sellController = new SellController();
        this.enhanceStoneController = new EnhanceStoneController(imageView, ItemData.ITEM_ID_MAP);
        this.itemValidator = new ItemValidator();
        this.currentItem = "돌";
        
        viewCollectionController = new ViewCollectionController(primaryStage, this);
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
            enhanceItem(currentItem);  // E 키를 눌렀을 때와 동일한 메서드 호출
            updateStatusAndProbability(currentItem);  // 상태 및 확률 업데이트
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Scene showGameScreen() {
        // 먼저 gameScene 변수를 선언합니다.
        Scene gameScene;
        
        Label welcomeLabel = new Label("사용자: " + playerId);
        welcomeLabel.setFont(new Font(24));
        Label fundsLabel = new Label("현재 자금: " + playerFunds + "원");
        fundsLabel.setFont(new Font(24));

        VBox topLeftBox = new VBox(10);
        topLeftBox.getChildren().addAll(welcomeLabel, fundsLabel);
        topLeftBox.setAlignment(Pos.TOP_LEFT);
        topLeftBox.setPadding(new Insets(10));

        Button enhanceButton = new Button("[E] 강화하기");
        setupEnhanceButton(enhanceButton);

        Button viewCollectionButton = new Button("[V] 도감 보기");
        viewCollectionButton.setOnAction(e -> openCollectionScreen());

        Button inventoryButton = new Button("인벤토리");
        inventoryButton.setOnAction(e -> openInventoryScreen());

        Button shopButton = new Button("[Q] 상점");
        shopButton.setOnAction(e -> openShopScreen());

        Button sellButton = new Button("[S] 판매하기");
        sellButton.setOnAction(e -> {
            sellItem(); 
            updateImage("돌"); 
            updateStatusAndProbability("돌"); // 판매 후 상태 및 확률 업데이트
        });

        VBox centerVBox = new VBox(10);
        centerVBox.getChildren().addAll(statusLabel, probabilityLabel, imageView, enhanceButton);
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

        // BorderPane을 새로 생성하고 멤버 변수에 할당합니다.
        borderPane = new BorderPane();
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
        updateStatusAndProbability(currentItem); // 게임 화면 초기화 시 상태 및 확률 업데이트

        String cssPath = getClass().getResource("/css/GameUi.css").toExternalForm();
        
        // Scene을 생성하고 초기화합니다.
        gameScene = new Scene(borderPane, 1000, 800);
        gameScene.getStylesheets().add(cssPath);
        
        primaryStage.setScene(gameScene);
        primaryStage.centerOnScreen();
        primaryStage.show();

        // 키보드 이벤트 핸들러 설정
        gameScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.E) {
                enhanceItem(currentItem); 
                updateStatusAndProbability(currentItem);
            } else if (event.getCode() == KeyCode.S) {
                sellItem();
                updateImage("돌"); 
                updateStatusAndProbability("돌");
            } else if (event.getCode() == KeyCode.V) {
                openCollectionScreen();
            } else if (event.getCode() == KeyCode.Q) {
                openShopScreen();
            }
        });

        return gameScene;
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
        System.out.println("Shop button clicked!");
        ShopController shopController = new ShopController(primaryStage, this, imageView);
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
        System.out.println("Attempting to load image from path: " + imagePath);
        
        InputStream imageStream = getClass().getResourceAsStream(imagePath);
        if (imageStream == null) {
            System.out.println("Image not found: " + imagePath);
            return;
        }
        
        Image newImage = new Image(imageStream, imageView.getFitWidth(), imageView.getFitHeight(), true, true);
        imageView.setImage(newImage);
        System.out.println("Image updated successfully to: " + itemName);
    }

    public String getCurrentItem() {
        return currentItem;
    }

    // 추가: 현재 상태 및 다음 단계 확률 업데이트
    public void updateStatusAndProbability(String currentItem) {
        // 현재 아이템의 판매 가격을 가져옵니다.
        Integer sellingPrice = sellController.calculateSellingPrice(currentItem);
        
        // 상태 레이블을 현재 아이템과 함께 판매 가격을 포함하도록 업데이트합니다.
        statusLabel.setText("현재 아이템: " + currentItem + " (판매 가격: " + sellingPrice + "원)");
        
        // 현재 아이템의 강화 단계와 확률을 업데이트합니다.
        EnhanceStage stage = enhanceStoneController.getEnhanceStage(currentItem);
        if (stage != null && !stage.getNextStages().isEmpty()) {
            double totalProbability = stage.getNextStages().values().stream().mapToDouble(Double::doubleValue).sum();
            probabilityLabel.setText("다음 단계로 넘어갈 확률: " + totalProbability + "%");
        } else {
            probabilityLabel.setText("강화 불가 현 단계에서 마지막 루트입니다.");
        }
    }

    public void updateFundsLabel() {
        // 예를 들어, BorderPane에서 상단에 위치한 VBox를 참조한다고 가정합니다.
        // VBox가 실제로 BorderPane에 설정된 것이 맞는지 확인해야 합니다.
        if (borderPane.getTop() instanceof VBox) {
            VBox topLeftBox = (VBox) borderPane.getTop();
            
            // topLeftBox에서 자금 레이블을 찾고 업데이트합니다.
            for (Node node : topLeftBox.getChildren()) {
                if (node instanceof Label) {
                    Label label = (Label) node;
                    if (label.getText().startsWith("현재 자금:")) {
                        label.setText("현재 자금: " + playerFunds + "원");
                    }
                }
            }
        } else {
            // 잘못된 형식이거나 설정되지 않은 경우 처리합니다.
            System.out.println("상단 영역이 VBox가 아닙니다.");
        }
    }

    public void sellItem() {
        if (currentItem != null && !currentItem.equals("돌")) {
            int sellingPrice = sellController.calculateSellingPrice(currentItem);
            playerFunds += sellingPrice;
            System.out.println(currentItem + "를 판매했습니다. 판매 금액: " + sellingPrice + " 현재 자금: " + playerFunds);

            // 판매 후 상태 업데이트
            currentItem = "돌"; // 판매 후 아이템을 '돌'로 초기화
            updateImage(currentItem);
            updateStatusAndProbability(currentItem);
            updateFundsLabel(); // 자금 레이블 업데이트
        } else {
            System.out.println("판매할 아이템이 없습니다.");
        }
    }

    public void setCurrentItem(String itemName) {
        this.currentItem = itemName;
        System.out.println("Setting current item to: " + itemName); // 디버깅 로그
        updateImage(currentItem);
        updateStatusAndProbability(currentItem);
    }

    public void updateItemImage(String imagePath) {
        // 이미지 업데이트
        Image image = new Image(getClass().getResourceAsStream(imagePath));
        imageView.setImage(image);
    }

    public void setViewCollectionController(ViewCollectionController controller) {
        this.viewCollectionController = controller;
    }
    public int getPlayerFunds() {
        return playerFunds;
    }
    private String getImagePathForItem(String itemName) {
        // 아이템 이름을 기반으로 파일 경로를 반환합니다.
        Integer itemId = ItemData.ITEM_ID_MAP.get(itemName);
        if (itemId != null) {
            return "/image/" + itemId + "-" + itemName + ".jpg";
        }
        return null;
    }
    // 자금을 차감하는 메소드
    public void deductFunds(int amount) {
        playerFunds -= amount;
    }
}
