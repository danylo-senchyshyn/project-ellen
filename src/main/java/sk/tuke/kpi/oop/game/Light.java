package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable, EnergyConsumer {
    private boolean isPowered;
    private boolean isOn;
    private final Animation onAnimation;
    private final Animation offAnimation;

    public Light() {
        isOn = true;
        isPowered = true;

        onAnimation = new Animation("sprites/light_on.png", 16, 16);
        offAnimation = new Animation("sprites/light_off.png", 16, 16);

        setAnimation(onAnimation);
    }

    public boolean isPower() {
        return isPowered;
    }

    @Override
    public void setPowered(boolean powered) {
        this.isPowered = powered;
        updateLightAnimation();
    }

    @Override
    public boolean isOn() {
        return isOn;
    }

    @Override
    public void turnOn() {
        this.isOn = true;
        updateLightAnimation();
    }

    @Override
    public void turnOff() {
        this.isOn= false;
        updateLightAnimation();
    }

    public void toggle() {
        isOn = !isOn;
        updateLightAnimation();
    }

    private void updateLightAnimation() {
        if (this.isOn) {
            if (this.isPowered) {
                setAnimation(onAnimation);
            } else {
                setAnimation(offAnimation);
            }
        }
    }

    @Override
    public void addedToScene(Scene scene) {
        super.addedToScene(scene);
    }
}
