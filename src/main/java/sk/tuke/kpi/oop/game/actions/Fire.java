package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

import java.util.Objects;

public class Fire <A extends Armed> extends AbstractAction<A> {

    @Override
    public void execute(float deltaTime) {
        if (getActor() == null) {
            setDone(true);
            return;
        }

        if (isDone()) {
            return;
        }

        Fireable fireable = getActor().getFirearm().fire();
        int pomX = Direction.fromAngle(getActor().getAnimation().getRotation()).getDx();
        int pomY = Direction.fromAngle(getActor().getAnimation().getRotation()).getDy();

        if (fireable!=null) {
            Objects.requireNonNull(getActor().getScene()).addActor(fireable, getActor().getPosX() + 8 + pomX*24, getActor().getPosY() + 8 + pomY*24);
            fireable.startedMoving(Direction.fromAngle(getActor().getAnimation().getRotation()));
            new Move<Fireable>(Direction.fromAngle(getActor().getAnimation().getRotation()),Float.MAX_VALUE).scheduleFor(fireable);
        }

        setDone(true);
    }
}
