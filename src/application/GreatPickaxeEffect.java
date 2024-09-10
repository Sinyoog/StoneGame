package Application;

public class GreatPickaxeEffect extends ItemEffect {
    @Override
    public void apply(GameController gameController) {
        gameController.updateItemImage("13-다이아몬드.jpg");
    }
}
