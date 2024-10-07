package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
    private boolean isPowered;
    private boolean isOn;
    private Animation lightOnAnimation;
    private Animation lightOffAnimation;

    // Constructor
    public Light() {
        this.isPowered = false;
        this.isOn = false;

        lightOnAnimation = new Animation("sprites/light_on.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);
        lightOffAnimation = new Animation("sprites/light_off.png", 16, 16, 0.1f, Animation.PlayMode.LOOP);

        setAnimation(lightOffAnimation);
    }

    public void toggle() {
        if (isPowered) {
            isOn = !isOn;
            updateAnimation();
        }
    }

    public void setElectricityFlow(boolean isPowered) {
        this.isPowered = isPowered;
        updateAnimation();
    }

    private void updateAnimation() {
        if (isOn && isPowered) {
            setAnimation(lightOnAnimation);
        } else {
            setAnimation(lightOffAnimation);
        }
    }

    // getters and setters
    public boolean isOn() {
        return isOn;
    }

    public boolean isPowered() {
        return isPowered;
    }
}
