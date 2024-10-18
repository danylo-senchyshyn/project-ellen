package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private final Animation controllerAnimation;
    private Reactor reactor;

    public Controller(Reactor reactor) {
        controllerAnimation = new Animation("sprites/switch.png", 16, 16);
        this.reactor = reactor;

        setAnimation(controllerAnimation);
    }

    public void toggle() {
        if (reactor.isRunning() && !reactor.isBroken()) {
            reactor.turnOff();
        } else {
            reactor.turnOn();
        }
    }
}
