package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;

    // Constructor Reactor
    public Reactor() {
        this.temperature = 0;
        this.damage = 0;

        normalAnimation = new Animation ("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation    ("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation ("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);

        setAnimation(normalAnimation);
    }

    public void increaseTemperature(int increment) {
        if (damage >= 100 || increment < 0) { return; }

        this.temperature += increment;

        if (temperature > 2000) {
            if (temperature <= 4000) {
                damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 50.0));
            } else if (temperature > 4000 && temperature < 6000) {
                damage = Math.max(damage, (int) Math.floor((temperature - 2000) / 25.0));
            } else if (temperature >= 6000) {
                damage = 100;
            }
        }

        if (damage < 33) {
            this.temperature += increment;
        } else if (damage < 66) {
            this.temperature += (int) Math.ceil(increment * 1.5);
        } else {
            this.temperature += (int) Math.ceil(increment * 2);
        }

        if (this.temperature >= 6000) {
            this.temperature = 6000;
            damage = 100;
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (damage >= 100 || decrement < 0) { return; }

        int effectiveDecrement = decrement;

        if (damage >= 50) {
            effectiveDecrement = decrement / 2;
        }

        // Уменьшаем текущую температуру, но не ниже 0
        this.temperature = Math.max(this.temperature - effectiveDecrement, 0);

        updateAnimation();
    }

    private void updateAnimation() {
        if (damage < 33) {
            normalAnimation.setFrameDuration(0.1f);
        } else if (damage < 66) {
            normalAnimation.setFrameDuration(0.05f);
        } else {
            normalAnimation.setFrameDuration(0.01f);
        }
        setAnimation(normalAnimation);
    }

    // setter and getter
    public int getTemperature() {
        return temperature;
    }
    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
