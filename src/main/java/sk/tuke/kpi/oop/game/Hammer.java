package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Hammer extends AbstractActor {
    private int remainingUses;
    Animation animation;

    public Hammer(Animation animation) {
        this.remainingUses = 1;
        this.animation = new Animation("sprites/hammer.png");
        setAnimation(animation);
    }

    public int getRemainingUses() {
        return remainingUses;
    }

    public void use() {
        remainingUses--;
        if (remainingUses <= 0) {
            Scene scene = getScene();
            if (scene != null) {
                scene.removeActor(this);
            }
        }
    }

    @Override
    public void removedFromScene(Scene scene) {
        super.removedFromScene(scene);
    }
}
