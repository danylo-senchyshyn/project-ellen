package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.tools.BreakableTool;

public class Wrench extends BreakableTool<DefectiveLight> {
    private Animation wrenchAnimation;

    public Wrench() {
        super(2);
        wrenchAnimation = new Animation("sprites/wrench.png");
        setAnimation(wrenchAnimation);
    }

    public Wrench(int remainingUses) {
        super(remainingUses);
    }

    @Override
    public void useWith(DefectiveLight actor) {
        if (actor == null) {
            return;
        } else if (actor.repair()) {
            super.useWith(actor);
        }
    }

}
