package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

import java.util.List;
import java.util.Objects;


public class Take<K extends Keeper> extends AbstractAction<K> {
    private final K keeper;

    public Take(K keeper) {
        this.keeper = keeper;
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null || getActor().getScene() == null || isDone()) {
            setDone(true);
            return;
        }

        Scene scene = keeper.getScene();
        List<Actor> toolList = Objects.requireNonNull(keeper.getScene()).getActors();

        for (Actor tool : toolList) {
            if (tool instanceof Collectible && tool.intersects(keeper)) {
                try {
                    keeper.getBackpack().add(((Collectible) tool));

                    assert scene != null;
                    scene.removeActor(tool);
                    break;

                } catch (IllegalStateException exception) {
                    scene.getOverlay().drawText(exception.getMessage(), 0, 0).showFor(2);
                }
            }
        }
        setDone(true);
    }
}
