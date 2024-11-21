package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {
    private Keeper keeper;

    public Drop(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void execute(float deltaTime) {
        Scene scene = keeper.getScene();

        if (getActor() == null || isDone() || getActor().getBackpack().peek() == null) {
            setDone(true);
            return;
        }

        Collectible lastTool = keeper.getBackpack().peek();
        assert lastTool != null;
        assert scene != null;
        scene.addActor(lastTool,
            (keeper.getPosX() + (keeper.getWidth() - lastTool.getWidth() / 2)),
            (keeper.getPosY() + (keeper.getHeight() - lastTool.getHeight() / 2)));
        keeper.getBackpack().remove(lastTool);
        setDone(true);
    }
}
