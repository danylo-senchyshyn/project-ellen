package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;

import java.util.Random;

public class Controller {
    private Animation animation;
    private Reactor reactor;
    private boolean running;

    public Controller(Reactor reactor) {
        this.animation = new Animation("sprites/switch.png", 16, 16);
        this.reactor = reactor;
    }

    public Reactor getReactor() {
        return reactor;
    }
    public void setReactor(Reactor reactor) {
        this.reactor = reactor;
    }


    public void toggle() {
        if (reactor != null) {
            if (reactor.isRunning()) {
                reactor.turnOff();
            } else {
                reactor.turnOn();
            }
        }
        reactor.updateAnimation();
    }


    public boolean isRunning() {
        return running;
    }
}
