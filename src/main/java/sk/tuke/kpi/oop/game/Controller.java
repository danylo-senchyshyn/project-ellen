package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Controller extends AbstractActor {
    private Reactor reactor;
    private Animation switchAnimation;

    // Конструктор, принимающий реактор в качестве параметра
    public Controller(Reactor reactor) {
        this.reactor = reactor;

        switchAnimation = new Animation("sprites/switch.png", 16, 16, 0.1f);
        setAnimation(switchAnimation);
    }

    // Метод для переключения состояния реактора
    public void toggle() {
        if (reactor != null) {
            if (reactor.isRunning()) {
                reactor.turnOff();
            } else {
                reactor.turnOn();
            }
        }
    }

    // Метод для получения текущего реактора
    public Reactor getReactor() {
        return reactor;
    }

    // Метод для замены управляемого реактора
    public void setReactor(Reactor reactor) {
        this.reactor = reactor;
    }
}
