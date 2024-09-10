package Application;

import javafx.application.Application;
import javafx.stage.Stage;

public class LoginApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        // LoginController 인스턴스 생성
        LoginController loginController = new LoginController(primaryStage);
        
        // 데이터베이스 초기화 (테스트용, 실제 배포 시 필요에 따라 호출)
        LoginController.initializeDatabase();
        
        // 로그인 화면 표시
        loginController.showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
