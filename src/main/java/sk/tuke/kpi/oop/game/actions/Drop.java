package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<A extends Keeper> extends AbstractAction<A> {


    @Override
    public void execute(float deltaTime) {

        if (getActor() == null || isDone() || getActor().getBackpack().peek() == null) {
            setDone(true);
            return;
        }

        Collectible lastTool = getActor().getBackpack().peek();
        assert lastTool != null;
        getActor().getScene().addActor(lastTool,
            (getActor().getPosX() + (getActor().getWidth() - lastTool.getWidth() / 2)),
            (getActor().getPosY() + (getActor().getHeight() - lastTool.getHeight() / 2)));
        getActor().getBackpack().remove(lastTool);
        setDone(true);
    }
}
