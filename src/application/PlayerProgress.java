package Application;

public class PlayerProgress {
    private int funds;
    private String inventory;
    private String currentItem;

    public PlayerProgress(int funds, String inventory, String currentItem) {
        this.funds = funds;
        this.inventory = inventory;
        this.currentItem = currentItem;
    }

    public int getFunds() {
        return funds;
    }

    public String getInventory() {
        return inventory;
    }

    public String getCurrentItem() {
        return currentItem;
    }
}
