package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor {
    private boolean isPowered;
    private boolean isOn;
    private final Animation onAnimation;
    private final Animation offAnimation;

    public Light() {
        isOn = false;
        isPowered = false;

        onAnimation = new Animation("sprites/light_on.png", 16, 16);
        offAnimation = new Animation("sprites/light_off.png", 16, 16);
        setAnimation(offAnimation);
    }

    public boolean getPower() {
        return isPowered;
    }

    public boolean getOn() {
        return isOn;
    }

    public void toggle() {
        isOn = !isOn;
        updateLightAnimation();
    }

    public void setElectricityFlow(boolean power) {
        this.isPowered = power;
        updateLightAnimation();
    }

    public void updateLightAnimation() {
        if (isOn && isPowered) {
            setAnimation(onAnimation);
        } else {
            setAnimation(offAnimation);
        }
    }
}
