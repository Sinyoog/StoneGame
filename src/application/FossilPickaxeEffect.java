package Application;

public class FossilPickaxeEffect extends ItemEffect {
    @Override
    public void apply(GameController gameController) {
        gameController.updateItemImage("23-화석.jpg");
    }
}
