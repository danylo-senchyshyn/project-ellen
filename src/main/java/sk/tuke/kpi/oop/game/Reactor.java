package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.framework.AbstractActor;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private boolean running;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;

    public Reactor() {
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png", 80, 80);
        setAnimation(normalAnimation);

        this.temperature = 0;
        this.damage = 0;
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || isBroken()) return;

        temperature += increment;

        if (temperature > 2000) {
            damage += (int) Math.floor((temperature - 2000) / 40);
            if (damage > 100) {
                damage = 100;
                temperature = 6000;
            }
        }

        if (damage < 33) {
            temperature += increment;
        } else if (damage < 66) {
            temperature += Math.ceil(increment * 1.5);
        } else {
            temperature += 2 * increment;
        }

        updateAnimation();

        if (temperature >= 6000) {
            damage = 100;
        }
    }

    public void decreaseTemperature(int decrement) {
        if (decrement < 0 || isBroken()) return;

        if (damage >= 50 || temperature >= 4000) {
            temperature -= decrement / 2;
        } else {
            temperature -= decrement;
        }

        updateAnimation();

        if (temperature < 0) {
            temperature = 0;
        }
    }

    public void updateAnimation() {
        if (!running) {
            setAnimation(offAnimation);
            return;
        } else if (damage >= 100 || temperature >= 6000) {
            setAnimation(brokenAnimation);
        } else if (temperature > 4000 || damage > 50) {
            setAnimation(hotAnimation);
        } else {
            setAnimation(normalAnimation);
        }
    }

    public void repairWith(Hammer hammer) {
        if (hammer == null && damage <= 0 && isBroken()) return;

        damage = Math.max(damage - 50, 0);
        temperature = ((6000 * damage) / 100);

        updateAnimation();
    }

    public void turnOn() {
        if (!isBroken()) {
            this.running = true;
            updateAnimation();
        }
    }
    public void turnOff() {
        this.running = false;
        setAnimation(offAnimation);
    }
    public boolean isRunning() {
        return running;
    }
    public boolean isBroken() {
        return damage >= 100;
    }

}
