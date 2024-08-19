package application;

import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.KeyCode;

import java.util.Arrays;

public class SellController {
    private Stage primaryStage;
    private String[] inventory;
    private int playerFunds;

    public SellController(Stage primaryStage, String[] inventory, int playerFunds) {
        this.primaryStage = primaryStage;
        this.inventory = inventory;
        this.playerFunds = playerFunds;
    }

    public void processSell() {
        if (inventory.length > 0) {
            String itemToSell = inventory[0];
            int sellingPrice = calculateSellingPrice(itemToSell);
            playerFunds += sellingPrice;
            inventory = removeItemFromInventory(inventory, itemToSell);
        } else {
            showError("판매할 아이템이 없습니다.");
        }
    }

    public int getPlayerFunds() {
        return playerFunds;
    }

    public String[] getInventory() {
        return inventory;
    }

    private int calculateSellingPrice(String item) {
        return 100; // 판매 가격 로직 (예시)
    }

    private String[] removeItemFromInventory(String[] inventory, String item) {
        return Arrays.stream(inventory)
                .filter(i -> !i.equals(item))
                .toArray(String[]::new);
    }

    private void showError(String message) {
        Stage errorStage = new Stage();
        VBox vbox = new VBox(new Label(message));
        Scene scene = new Scene(vbox, 200, 100);
        errorStage.setScene(scene);
        errorStage.show();
    }
}
