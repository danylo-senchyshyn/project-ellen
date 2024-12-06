package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {
//    private Keeper keeper;
//
//    public Drop(Keeper keeper) {
//        this.keeper = keeper;
//    }

    @Override
    public void execute(float deltaTime) {
        //Scene scene = keeper.getScene();

        if (getActor() == null || isDone() || getActor().getBackpack().peek() == null) {
            setDone(true);
            return;
        }

        Collectible lastTool = getActor().getBackpack().peek();
        assert lastTool != null;
        //assert scene != null;
        getActor().getScene().addActor(lastTool,
            (getActor().getPosX() + (getActor().getWidth() - lastTool.getWidth() / 2)),
            (getActor().getPosY() + (getActor().getHeight() - lastTool.getHeight() / 2)));
        getActor().getBackpack().remove(lastTool);
        setDone(true);
    }
}
