package Application;

public class GoodPickaxeEffect extends ItemEffect {
    @Override
    public void apply(GameController gameController) {
        gameController.updateItemImage("11-루비.jpg");
    }
}
