package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ImageExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Image Example");

        // 이미지 로드
        Image image = new Image("file:path_to_image.png"); // 이미지 경로
        ImageView imageView = new ImageView(image);

        // 버튼
        Button button = new Button("클릭");

        // 레이아웃
        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, button);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
