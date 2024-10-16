package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Reactor extends AbstractActor {
    private int temperature;
    private int damage;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation offAnimation;


    public Reactor(int temperature, int damage) {
        this.temperature = temperature;
        this.damage = damage;
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        offAnimation = new Animation("sprites/reactor.png", 80, 80);

        setAnimation(normalAnimation);
    }

    public void getTemperature() {
        this.temperature = temperature;
    }
    public int getDamage() {
        return damage;
    }

    public void increaseTemperature(int increment) {
        if (damage < 33) {
            temperature += increment;
        } else if (damage < 66) {
            temperature += Math.ceil(increment * 1.5);
        } else {
            temperature += increment * 2;
        }
        if (temperature >= 2000) {
            damage = (int) Math.floor((temperature - 2000) / 40);
            if (damage > 100) {
                damage = 100;
            }
        }

        updateAnimation();
    }

    public void decreaseTemperature(int decrement) {
        if (damage < 50) {
            temperature -= decrement;
        } else if (damage < 100) {
            temperature -= Math.ceil(decrement * 1.5);
        }

        updateAnimation();
    }

    public void updateAnimation() {
        if (temperature < 4000) {
            setAnimation(normalAnimation);
        } else if (temperature < 6000) {
            setAnimation(hotAnimation);
        } else {
            setAnimation(brokenAnimation);
        }
    }
}
