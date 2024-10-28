package sk.tuke.kpi.oop.game.tools;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Reactor;

public class FireExtinguisher extends BreakableTool<Reactor> {
    private final Animation extinguisherAnimation;

    public FireExtinguisher() {
        super(1);
        extinguisherAnimation = new Animation("sprites/extinguisher.png");
        setAnimation(extinguisherAnimation);
    }

    @Override
    public void useWith(Reactor reactor) {
        if (reactor == null) return;

        if (reactor.extinguish()) {
            this.useWith(reactor);
        }
    }
}
