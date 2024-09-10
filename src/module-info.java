module Project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql; // 추가: JDBC 관련 클래스들을 사용하기 위해

    opens Application to javafx.fxml;
    exports Application;
}
