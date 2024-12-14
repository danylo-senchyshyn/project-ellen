package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

import java.util.Objects;

public class Energy extends AbstractActor implements Usable<Alive> {

    public Energy() {
        Animation energyAnimation = new Animation("sprites/energy.png", 16, 16);
        setAnimation(energyAnimation);
    }

    @Override
    public void useWith(Alive ripley) {
        if (ripley == null || ripley.getHealth().getValue() == 100) return;

        if (ripley.getHealth().getValue() < 100) {
            ripley.getHealth().restore();

            (Objects.requireNonNull(getScene())).removeActor(this);
        }
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }
}
