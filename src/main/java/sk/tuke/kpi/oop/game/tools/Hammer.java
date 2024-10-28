package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class Hammer extends BreakableTool<Reactor> {
    private int remainingUses;

    public Hammer() {
        this(1);
    }

    public Hammer(int remainingUses) {
        super(remainingUses);
        Animation hammerAnimation = new Animation("sprites/hammer.png");
        setAnimation(hammerAnimation);
    }


    public int getRemainingUses() {
        return remainingUses;
    }

    @Override
    public void useWith(Reactor reactor) {
        if (reactor == null) return;

        if (reactor.repair()) {
            this.useWith(reactor);
        }
    }
}
