package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.tools.FireExtinguisher;
import sk.tuke.kpi.oop.game.tools.Hammer;
import sk.tuke.kpi.oop.game.tools.Mjolnir;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean isOn;
    private Light light;
    private FireExtinguisher fireExtinguisher;
    private final Animation normalAnimation;
    private final Animation hotAnimation;
    private final Animation brokenAnimation;
    private final Animation offAnimation;
    private final Animation extinguishedAnimation;
    private Set<EnergyConsumer> devices;

    public Reactor() {
        this.temperature = 0;
        this.damage = 0;
        this.isOn = false;
        this.light = null;
        this.fireExtinguisher = null;
        devices = new HashSet<>();

        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.01f);
        offAnimation = new Animation("sprites/reactor.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        extinguishedAnimation = new Animation("sprites/reactor_extinguished.png",80,80);

        setAnimation(offAnimation);
    }

    public int getTemperature() {
        return temperature;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public Animation getAnimation() {
        return super.getAnimation();
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
        new PerpetualReactorHeating(1).scheduleFor(this);
    }

    public void addDevice(EnergyConsumer device) {
        this.devices.add(device);
        if (damage == 0 && isOn()) {
            device.setPowered(true);
        }
        device.setPowered(isOn());
    }

    public void removeDevice(EnergyConsumer device){
        device.setPowered(false);
        this.devices.remove(device);

    }

    public void increaseTemperature(int increment) {
        if (increment < 0 || !isOn) return;

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
        if (decrement > 0 && !isBroken() && isOn) {
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
            if (isOn && damage < 100) {
                light.setPowered(true);
            } else {
                light.setPowered(false);
            }
        }
    }

    public boolean repair() {
        if ((damage > 0) && (damage < 100)) {
            temperature = ((damage - 50) * 40) + 2000;
            damage = Math.max(damage - 50, 0);
            updateAnimation();
            return true;
        }
        return false;
    }

    public boolean extinguish() {
        if (!isOn || damage == 0) {
            return false;
        } else {
            this.temperature = this.getTemperature() - 4000;
            setAnimation(extinguishedAnimation);
            return true;
        }
    }

    public void turnOn() {
        this.isOn = true;
        updateAnimation();
    }

    public void turnOff() {
        this.isOn = false;
        updateAnimation();
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isBroken() {
        return damage >= 100;
    }

    public boolean isLaunched() {
        return isOn && light != null && light.isOn() && light.getPower();
    }
}
