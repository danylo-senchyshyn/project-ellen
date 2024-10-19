package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Cooler extends AbstractActor {
    private boolean isOn;
    private final Animation fanAnimation;
    private Reactor reactor;

    public Cooler(Reactor reactor) {
        this.reactor = reactor;
        fanAnimation = new Animation("sprites/fan.png", 32, 32, 0.2f);
        setAnimation(fanAnimation);
        fanAnimation.pause();
        isOn = false;
    }

    public Reactor getReactor() {
        return reactor;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        isOn = true;
        fanAnimation.play();
    }

    public void turnOff() {
        isOn = false;
        fanAnimation.pause();
    }

    public void toggle() {
        isOn = !isOn;
    }

    private void coolReactor() {
        if (reactor == null) {
            return;
        }
        else if (isOn) {
            this.reactor.decreaseTemperature(1);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::coolReactor)).scheduleFor(this);
    }
}
