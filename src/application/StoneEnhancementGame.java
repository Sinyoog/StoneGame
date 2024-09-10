package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class StoneEnhancementGame extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 데이터베이스 초기화
        LoginController.initializeDatabase();
        
        // 로그인 화면 표시
        LoginController loginController = new LoginController(primaryStage);
        loginController.showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
