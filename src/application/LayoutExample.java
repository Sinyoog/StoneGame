package Application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LayoutExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("JavaFX Example");

        // 레이아웃 설정
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(new Label("Hello, JavaFX!"));

        // 씬 설정
        Scene scene = new Scene(borderPane, 400, 300);
        primaryStage.setScene(scene);

        // 스테이지 보이기
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
