package Application;

public class ItemValidator {
    public void validateItem(String itemName) {
        if (!ItemData.ITEM_ID_MAP.containsKey(itemName)) {
            System.out.println("Invalid item: " + itemName);
        } else {
            System.out.println("Item is valid: " + itemName);
        }
    }
}
