package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends BreakableTool<Actor> {
    private int remainingUses;

    public Hammer() {
        super(1);
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }

    public int getRemainingUses() {
        return remainingUses;
    }

//    @Override
//    public void useWith(Actor actor) {
//        super.useWith(actor);
//    }
}
