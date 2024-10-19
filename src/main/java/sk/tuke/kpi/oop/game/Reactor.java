package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.oop.game.tools.Mjolnir;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean running;
    private Light light;
    private FireExtinguisher fireExtinguisher;
    private final Animation normalAnimation;
    private final Animation hotAnimation;
    private final Animation brokenAnimation;
    private final Animation offAnimation;
    private final Animation extinguishedAnimation;

    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.running = false;
        this.light = null;
        this.fireExtinguisher = null;

        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png", 80, 80);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png");

        setAnimation(offAnimation);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !running) return;

        if (damage < 33) {
            temperature += increment;
        } else if (damage < 66) {
            temperature += (int) Math.round(increment * 1.5);
        } else {
            temperature += increment * 2;
        }
        if (temperature >= 2000) {
            damage = (int) Math.ceil((double) (temperature - 2000) / 40);
            if (damage > 100) {
                damage = 100;
            }
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (decrement > 0 && !isBroken() && running) {
            if (damage < 50) {
                temperature -= decrement;
            } else if (damage < 100) {
                temperature -= (int) Math.ceil(0.5f * decrement);
            }
        }
        if (temperature < 0) {
            temperature = 0;
        }

        updateAnimation();
    }

    public void updateAnimation() {
        if (!isLaunched()) {
            setAnimation(offAnimation);
        } else {
            if (temperature < 4000) {
                setAnimation(normalAnimation);
            } else if (temperature < 6000) {
                setAnimation(hotAnimation);
            } else {
                setAnimation(brokenAnimation);
            }
        }

        updateLight();
    }

    public void updateLight() {
        if (light != null) {
            if (running && damage < 100) {
                light.setElectricityFlow(true);
            } else {
                light.setElectricityFlow(false);
            }
        }
    }

    public void repairWith(Object object) {
        if (object != null && damage > 0 && damage < 100) {
            int previousDamage = damage;
            damage = Math.max(damage - 50, 0);

            if (object instanceof Hammer) {
                ((Hammer) object).use();
            } else if (object instanceof Mjolnir) {
                ((Mjolnir) object).use();
            }

            int auxiliaryDamage = previousDamage - 50;
            int newTemperature = 2000 + (auxiliaryDamage * 40);

            if (newTemperature < temperature) {
                temperature = newTemperature;
            }
        }
        updateAnimation();
    }

    public void turnOn() {
        this.running = true;
        updateAnimation();
    }

    public void turnOff() {
        this.running = false;
        updateAnimation();
    }

    public void addLight(Light light) {
        this.light = light;
        updateLight();
    }

    public void removeLight() {
        if (this.light != null) {
            this.light.setElectricityFlow(false);
            this.light = null;
        }
        updateLight();
    }

    public void extinguishWith(FireExtinguisher fireExtinguisher) {
        if (fireExtinguisher == null || fireExtinguisher.getRemainingUses() <= 0 || !isBroken()) return;
        this.temperature = 4000;
        setAnimation(extinguishedAnimation);
        fireExtinguisher.use();
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isBroken() {
        return damage >= 100;
    }

    public boolean isLaunched() {
        return running && light != null && light.getOn() && light.getPower();
    }
}
