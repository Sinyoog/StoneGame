package Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
	private static final String DB_URL = "jdbc:sqlite:datastore/test.db";
    private Stage primaryStage;

    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

 // LoginController.java
    public void showLoginScreen() {
        primaryStage.setTitle("돌 강화 게임");

        // ID와 비밀번호 입력 필드
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // 로그인 버튼
        Button loginButton = new Button("로그인");
        loginButton.setOnAction(e -> {
            String playerId = idField.getText();
            String playerPassword = passwordField.getText();

            // 유효성 검사
            if (playerId.isEmpty() || playerPassword.isEmpty()) {
                showError("ID와 비밀번호를 입력하세요!");
                return;
            }

            // 데이터베이스에서 사용자 정보 확인
            if (validateUser(playerId, playerPassword)) {
                // 로그인 성공, 게임 화면으로 전환
                showGameScreen(playerId);
            } else {
                showError("ID 또는 비밀번호가 잘못되었습니다!");
            }
        });

        // 회원가입 버튼
        Button registerButton = new Button("회원가입");
        registerButton.setOnAction(e -> showRegisterScreen());

        // 엔터 키로 로그인 버튼 클릭 이벤트 발생
        idField.setOnAction(e -> loginButton.fire());
        passwordField.setOnAction(e -> loginButton.fire());

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new Label("돌 강화 게임"), idField, passwordField, loginButton, registerButton);

        Scene scene = new Scene(vbox, 500, 300);

        // CSS 파일 추가
        String cssPath = "/style.css";
        URL cssUrl = getClass().getResource(cssPath);
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("CSS file not found: " + cssPath);
        }

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegisterScreen() {
        primaryStage.setTitle("회원가입");

        // ID와 비밀번호 입력 필드
        TextField idField = new TextField();
        idField.setPromptText("Enter ID");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");

        // 회원가입 버튼
        Button registerButton = new Button("회원가입");
        registerButton.setOnAction(e -> {
            String playerId = idField.getText();
            String playerPassword = passwordField.getText();

            // 유효성 검사
            if (playerId.isEmpty() || playerPassword.isEmpty()) {
                showError("ID와 비밀번호를 입력하세요!");
                return;
            }

            // 데이터베이스에 회원 정보 저장
            if (saveUserData(playerId, playerPassword)) {
                showError("회원가입 성공! 이제 로그인하세요.");
                showLoginScreen();
            } else {
                showError("이미 존재하는 ID입니다.");
            }
        });

        VBox vbox = new VBox(10);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(new Label("회원가입"), idField, passwordField, registerButton);

        Scene scene = new Scene(vbox, 300, 200);

        // CSS 파일 추가
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showGameScreen(String playerId) {
        String[] emptyInventory = new String[0]; // 빈 배열 생성
        GameController gameController = new GameController(primaryStage, playerId, 0, emptyInventory);
        gameController.showGameScreen();
    }

    private void showError(String message) {
        // 오류 메시지를 보여주는 간단한 방법
        Stage errorStage = new Stage();
        VBox vbox = new VBox(new Label(message));
        Scene scene = new Scene(vbox, 200, 100);
        errorStage.setScene(scene);
        errorStage.show();
    }

    private boolean saveUserData(String playerId, String playerPassword) {
        // 데이터베이스 연결 및 사용자 정보 저장
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            // 이미 존재하는 ID인지 확인
            String checkSql = "SELECT id FROM users WHERE id = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, playerId);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        return false; // 이미 존재하는 ID
                    }
                }
            }

            // 새 사용자 정보 저장
            String sql = "INSERT INTO users (id, password) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, playerId);
                pstmt.setString(2, playerPassword);
                pstmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    private boolean validateUser(String playerId, String playerPassword) {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, playerId);
                pstmt.setString(2, playerPassword);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next(); // 사용자가 존재하고 비밀번호가 일치하면 true 반환
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    // 데이터베이스 초기화 및 테이블 생성 (테스트용)
    public static void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL)) {
            String sql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id TEXT PRIMARY KEY NOT NULL," +
                    "password TEXT NOT NULL)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.execute();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}