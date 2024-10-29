package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable, Repairable{
    private int temperature;
    private int damage;
    private boolean isOn;
    private Light light;
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
        } else if (damage <= 66) {
            temperature += (int)(1.5 * increment);
        } else {
            temperature += increment * 2;
        }

        if (temperature > 2000) {
            this.damage = Math.round((temperature - 2000) / 40);
            if (damage >= 100) {
                damage = 100;
                isOn = false;
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
        if (!isOn) {
            if (damage < 100) {
                setAnimation(offAnimation);
            } else {
                setAnimation(brokenAnimation);
            }
        } else if (temperature < 0) {
            return;
        } else if (temperature <= 4000) {
            setAnimation(normalAnimation);
        } else if (temperature < 6000){
            setAnimation(hotAnimation);
        } else {
            setAnimation(brokenAnimation);
        }
        updateAllDevices();
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

    private void updateStateOfDevice(EnergyConsumer device){
        device.setPowered(isElectricity());
    }

    private void updateAllDevices(){
        this.devices.forEach(this::updateStateOfDevice);
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
        if (this.getDamage() >= 100) {
            isOn = false;
        } else {
            this.isOn = true;
            updateAnimation();
        }
    }

    public void turnOff() {
        if (temperature >= 6000) {
            setAnimation(brokenAnimation);
        } else {
            setAnimation(offAnimation);
        }
        isOn = false;
    }

    public boolean isOn() {
        return isOn;
    }

    public boolean isBroken() {
        return damage >= 100;
    }

    public boolean isLaunched() {
        return isOn && light != null && light.isOn() && light.isPower();
    }

    private boolean isElectricity(){
        return isOn() && damage != 100;
    }
}
