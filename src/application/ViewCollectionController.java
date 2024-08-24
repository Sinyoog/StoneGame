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
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.util.Map;

public class ViewCollectionController {
    private Stage primaryStage;
    private GameController gameController;
    private GridPane collectionGrid;
    private static final String ITEM_IMAGE_DIRECTORY = "/image"; // 아이템 이미지 디렉토리
    private static final int GRID_COLS = 7; // GridPane의 열 수
    private static final int GRID_ROWS = 5; // GridPane의 행 수

    // 아이템 위치를 저장할 배열
    private ImageView[][] itemImages;

    public ViewCollectionController(Stage primaryStage, GameController gameController) {
        this.primaryStage = primaryStage;
        this.gameController = gameController;
        this.collectionGrid = new GridPane();
        this.collectionGrid.setHgap(10);
        this.collectionGrid.setVgap(10);
        this.collectionGrid.setPadding(new Insets(10));

        // 셀 초기화
        itemImages = new ImageView[GRID_COLS][GRID_ROWS];
        initializeGrid();

        // 모든 아이템의 이미지를 업데이트
        updateItemImages();
    }

    private void initializeGrid() {
        for (int row = 0; row < GRID_ROWS; row++) {
            for (int col = 0; col < GRID_COLS; col++) {
                ImageView imageView = new ImageView();
                imageView.setFitWidth(92);
                imageView.setFitHeight(91);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                collectionGrid.add(imageView, col, row);
                itemImages[col][row] = imageView;
            }
        }
    }

    public void updateItemImage(String itemName) {
        Integer itemId = ItemData.ITEM_ID_MAP.get(itemName);
        if (itemId == null) {
            System.out.println("Item ID not found for: " + itemName);
            return;
        }

        // 위치 계산
        int index = itemId - 1; // 아이템 ID로 인덱스 계산
        int col = index % GRID_COLS;
        int row = index / GRID_COLS;

        String imagePath = ITEM_IMAGE_DIRECTORY + "/" + itemId + "-" + itemName + ".jpg";
        URL imageUrl = getClass().getResource(imagePath);

        if (imageUrl != null) {
            Image image = new Image(imageUrl.toExternalForm());
            ImageView imageView = itemImages[col][row];
            if (imageView != null) {
                imageView.setImage(image);
                System.out.println("이미지 업데이트 완료: " + itemName + " 위치: col=" + col + ", row=" + row);
            } else {
                System.out.println("해당 위치의 이미지 뷰가 존재하지 않습니다: col=" + col + ", row=" + row);
            }
        } else {
            System.out.println("이미지 파일을 찾을 수 없습니다: " + imagePath);
        }
    }

    public void showCollectionScreen() {
        Label titleLabel = new Label("보석 도감");
        titleLabel.setFont(new javafx.scene.text.Font(24));

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titleLabel, collectionGrid);
        vbox.setAlignment(Pos.CENTER);

        Button backButton = new Button("뒤로 가기");
        backButton.setOnAction(e -> primaryStage.setScene(gameController.showGameScreen()));

        VBox mainLayout = new VBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setPadding(new Insets(20));
        mainLayout.getChildren().addAll(vbox, backButton);

        Scene collectionScene = new Scene(mainLayout, 1000, 800);
        collectionScene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.setScene(gameController.showGameScreen());
            }
        });

        primaryStage.setScene(collectionScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void updateItemImages() {
        // 아이템 ID와 이름을 순서대로 가져와서 이미지 업데이트
        for (Map.Entry<String, Integer> entry : ItemData.ITEM_ID_MAP.entrySet()) {
            String itemName = entry.getKey();
            Integer itemId = entry.getValue();
            String imagePath = ITEM_IMAGE_DIRECTORY + "/" + itemId + "-" + itemName + ".jpg";
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                Image image = new Image(imageUrl.toExternalForm());

                int index = itemId - 1; // 아이템 ID로 인덱스 계산
                int col = index % GRID_COLS;
                int row = index / GRID_COLS;

                if (col >= 0 && col < GRID_COLS && row >= 0 && row < GRID_ROWS) {
                    ImageView imageView = itemImages[col][row];
                    if (imageView != null) {
                        imageView.setImage(image);
                        System.out.println("이미지 업데이트 완료: " + itemName + " 위치: col=" + col + ", row=" + row);
                    } else {
                        System.out.println("해당 위치의 이미지 뷰가 존재하지 않습니다: col=" + col + ", row=" + row);
                    }
                } else {
                    System.out.println("계산된 col 또는 row가 유효하지 않습니다: col=" + col + ", row=" + row);
                }
            } else {
                System.out.println("이미지 파일을 찾을 수 없습니다: " + imagePath);
            }
        }
    }
}
