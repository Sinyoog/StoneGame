package Application;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;

public class DataManager {
    private static final String SAVE_FILE = "game_data.txt";

    public static void saveGameData(String playerId, String playerPassword) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE))) {
            writer.write(playerId + "\n");
            writer.write(playerPassword + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadGameData(TextField idField, PasswordField passwordField) {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String playerId = reader.readLine();
                String playerPassword = reader.readLine();

                idField.setText(playerId);
                passwordField.setText(playerPassword);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
