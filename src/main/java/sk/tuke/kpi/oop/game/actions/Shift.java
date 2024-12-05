package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;

public class Shift<K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {
        if (getActor() == null || isDone() || getActor().getBackpack().peek() == null) {
            setDone(true);
            return;
        }

        getActor().getBackpack().shift();
        setDone(true);
    }
}
