package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Engine extends AbstractActor {
    public Engine() {
        Animation animation = new Animation("maps/alienbreed-sprites/engine.png", 96, 64, 0.1f, Animation.PlayMode.LOOP);
        setAnimation(animation);
        animation.setRotation(270);
    }

    @Override
    public void addedToScene(Scene scene) {
       super.addedToScene(scene);
    }
}
