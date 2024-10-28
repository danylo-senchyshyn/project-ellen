package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Mjolnir extends BreakableTool<Actor> {
    private final Animation hammerAnimation;

    public Mjolnir() {
        super(4);
        hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    @Override
    public void useWith(Actor actor) {
        super.useWith(actor);
    }
}
