package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.DefectiveLight;

public class Wrench extends BreakableTool<DefectiveLight> implements Collectible {
    public Wrench() {
        super(2);
        Animation wrenchAnimation = new Animation("sprites/wrench.png");
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
