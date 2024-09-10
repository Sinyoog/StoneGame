module Project {
    requires javafx.controls;
    requires java.sql; // 이 줄을 추가합니다
    
    opens application to javafx.graphics, javafx.fxml;
}

