package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GameDatabase {
	private static final String DB_URL = "jdbc:sqlite:C:/Users/MY/OneDrive/바탕 화면/datastore/game.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initialize() {
        try (Connection conn = connect()) {
            // 사용자 및 게임 진행 상태 관련 테이블 생성
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
